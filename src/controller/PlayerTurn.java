package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Board;
import model.Deck;
import model.Hand;
import model.Piece;
import model.Player;
import model.QwirkleGame;
import view.BoardListener;
import view.HandListener;
import view.QwirkleView;
import view.ViewListener;

/**
 *
 * @author laritz_m
 */
public class PlayerTurn extends AbstractFSM<PlayerTurn.TurnState> implements ViewListener, HandListener, BoardListener
{
    public static final Color SWAP_COLOR = Color.RED;
    public static final Color PLACE_COLOR = Color.YELLOW;
    
    public static enum TurnState {SETUP, START, SWAP, PLACE_PIECE, END_TURN};
    
    public static Map<TurnState, EnumSet<TurnState>> stateMap;
    
    static{
        stateMap = new HashMap<>();
        stateMap.put(TurnState.SETUP, EnumSet.of(TurnState.START));
        stateMap.put(TurnState.START, EnumSet.of(TurnState.SWAP, TurnState.PLACE_PIECE));
        stateMap.put(TurnState.SWAP, EnumSet.of(TurnState.START, TurnState.END_TURN));
        stateMap.put(TurnState.PLACE_PIECE, EnumSet.of(TurnState.START, TurnState.PLACE_PIECE, TurnState.SWAP, TurnState.END_TURN));
        stateMap.put(TurnState.END_TURN, EnumSet.noneOf(TurnState.class));
    }

    private Player currentPlayer;
    private int current;
    private QwirkleMachine fsm;
    private QwirkleView view;
    private QwirkleGame game;
    
    private List<Integer> rows;
    private List<Integer> cols;
    
    public PlayerTurn(QwirkleGame game, QwirkleView view, QwirkleMachine fsm, int current) {
        super(stateMap, TurnState.SETUP);
        this.game = game;
        this.view = view;
        this.fsm = fsm;
        this.currentPlayer = game.getPlayer(current);
        this.current = current;
    }

    @Override
    protected void stateStarted(TurnState state) 
    {        
        switch(state)
        {
            case SETUP:
                view.setStatus(current, "Taking turn: start");
                if(current == 0)
                    view.addPlayer1HandListener(this);
                else
                    view.addPlayer2HandListener(this);
                view.addButtonListener(this);
                view.addBoardListener(this);
                this.setState(TurnState.START);
                break;
            case START:
                view.setStatus(current, "Taking turn");
                
                break;
            case SWAP:
                for(int r = 0; r < game.getBoard().getNumRows(); r++)
                    for(int c = 0; c < game.getBoard().getNumCols(); c++)
                        view.setBoardHighlight(r, c, null);
                for (int i=0; i<Hand.MAX_SIZE; i++)
                    view.setPlayerHighlight(current, i, null);
                view.setStatus(current, "Swapping pieces");
                
                break;
            case PLACE_PIECE:
                for(int r = 0; r < game.getBoard().getNumRows(); r++)
                    for(int c = 0; c < game.getBoard().getNumCols(); c++)
                        view.setBoardHighlight(r, c, null);
                view.setStatus(current, "Placing pieces");
                rows = new ArrayList<>();
                cols = new ArrayList<>();
                break;
            case END_TURN:
                
                currentPlayer.fillHand(game.getDeck());
                fsm.finishTurn(this);
                break;
        }
        view.repaint();
    }

    @Override
    protected void stateEnded(TurnState state) 
    {
        switch(state)
        {
            case SWAP:
                Hand hand = currentPlayer.getHand();
                Deck deck = game.getDeck();
                int index = 0;
                while(index < hand.getSize())
                {
                    if (view.getPlayerHighlight(current, index) != null)
                    {
                        deck.addPiece(hand.removePiece(index));
                        int copy = index;
                        while(copy < hand.getSize() - 1)
                        {
                            view.setPlayerHighlight(current, copy, view.getPlayerHighlight(current, copy+1));
                            copy++;
                        }
                        view.setPlayerHighlight(current, hand.getSize(), null);
                    }
                    else
                    {
                        view.setPlayerHighlight(current, index, null);
                        index++;
                    }
                }
                view.repaint();
                break;
            case PLACE_PIECE:
                for (int i=0; i<Hand.MAX_SIZE; i++)
                    view.setPlayerHighlight(current, i, null);
                rows.clear();
                cols.clear();
                break;
                
        }
    }
    

    @Override
    public void swapButtonPressed() 
    {
        Hand hand = currentPlayer.getHand();
        
        if(this.getState() == TurnState.START)
        {
           this.setState(TurnState.SWAP);
        }
        else if(this.getState() == TurnState.SWAP)
        {
            for (int i=0; i<Hand.MAX_SIZE; i++)
                if(view.getPlayerHighlight(current, i) != null)
                {
                    this.setState(TurnState.END_TURN);
                    return;
                }
        }
        else if(this.getState() == TurnState.PLACE_PIECE && rows.isEmpty())
        {
           this.setState(TurnState.SWAP);
        }
    }

    @Override
    public void endTurnButtonPressed() {
        Hand hand = currentPlayer.getHand();
        if(this.getState() == TurnState.PLACE_PIECE  && hand.getSize() < Hand.MAX_SIZE)
        {
            currentPlayer.fillHand(game.getDeck());
            
            for (int index=0; index<hand.getSize(); index++)
                view.setPlayerHighlight(current, index, null);
            
            int[] rowsCopy = new int[rows.size()];
            int[] colsCopy = new int[cols.size()];
            
            for(int i = 0; i < rows.size(); i++) {
                rowsCopy[i] = rows.get(i);
                colsCopy[i] = cols.get(i);
            }
            
            Player p = game.getPlayer(current);
            p.addScore(QwirkleRuleSet.getMoveScore(game.getBoard(), rowsCopy, colsCopy));
            
            view.repaint();
            this.setState(TurnState.END_TURN);
        }
        
    }
    
    public static boolean sameValues(List<Integer> values)
    {
        for(Integer obj1 : values)
            for(Integer obj2 : values)
                if(!obj1.equals(obj2))
                    return false;
        return true;
    }

    @Override
    public void undoButtonPressed() {
        Hand hand = currentPlayer.getHand();
        if(this.getState() == TurnState.SWAP)
        { 
            for (int index=0; index<hand.getSize(); index++)
                view.setPlayerHighlight(current, index, null);
            this.setState(TurnState.START);
        }
        if(this.getState() == TurnState.PLACE_PIECE)
        {
            if(rows.size() <= 1)
            {
                for (int index=0; index<hand.getSize(); index++)
                    view.setPlayerHighlight(current, index, null);

                for(int i = 0; i < rows.size(); i++) {
                    game.getPlayer(current).getHand().addPiece(game.getBoard().removePiece(rows.get(i), cols.get(i)));
                }
                view.update();
                view.repaint();
                this.setState(TurnState.START);
            }
            else
            {
                int piece = rows.size() - 1;
                game.getPlayer(current).getHand().addPiece(game.getBoard().removePiece(rows.get(piece), cols.get(piece)));
                rows.remove(piece);
                cols.remove(piece);
                view.update();
                view.repaint();
            }
        }
    }
    
    public void unregister()
    {
        if(current == 0)
            view.removePlayer1HandListener(this);
        else
            view.removePlayer2HandListener(this);
        view.removeButtonListener(this);
    }

    @Override
    public void handClicked(int index) 
    {
        Hand hand = currentPlayer.getHand();
        
        if(this.getState() == TurnState.START)                          //start phase
        {
           this.setState(TurnState.PLACE_PIECE);
           
           if(view.getPlayerHighlight(current, index) == null)
               
                view.setPlayerHighlight(current, index, PLACE_COLOR);
           else
               view.setPlayerHighlight(current, index, null);
        }
        else if(this.getState() == TurnState.PLACE_PIECE)               //place piece phase
        {
            if(index < hand.getSize())
            {
                if(view.getPlayerHighlight(current, index) != null) {
                    view.setPlayerHighlight(current, index, null);
                }
                else
                    view.setPlayerHighlight(current, index, PLACE_COLOR);
            }
            for(int i = 0; i < hand.getSize();i++)
                if(i != index)
                    view.setPlayerHighlight(current, i, null);
        }
        else if(this.getState() == TurnState.SWAP && index < hand.getSize())                      //swap phase
        {
            if(view.getPlayerHighlight(current, index) == null)
                view.setPlayerHighlight(current, index, SWAP_COLOR);
            else
                view.setPlayerHighlight(current, index, null);
        }
        view.repaint();
    }
    
    @Override
    public void buttonClicked(int row, int col) 
    {
        Hand hand = currentPlayer.getHand();
        Board<Piece> board = game.getBoard();
        if(this.getState() == TurnState.PLACE_PIECE)
        {
            /*for(int r = 0; r < game.getBoard().getNumRows(); r++)
                for(int c = 0; c < game.getBoard().getNumCols(); c++)
                    view.setBoardHighlight(r, c, null);*/
            
            for (int index=0; index<hand.getSize(); index++)
            {
                if(view.getPlayerHighlight(current, index) != null)
                {
                    List<Piece> played = new ArrayList<>();
                    for(int i = 0; i < rows.size(); i++)
                        played.add(board.getPiece(rows.get(i), cols.get(i)));
                    
                    int[] rowsCopy = new int[rows.size()];
                    int[] colsCopy = new int[cols.size()];
                    
                    for(int i = 0; i < rows.size(); i++)
                    {
                        rowsCopy[i] = rows.get(i);
                        colsCopy[i] = cols.get(i);
                    }
                    
                    if (QwirkleRuleSet.isLegal(board, hand.getPiece(index), row, col) &&
                            QwirkleRuleSet.canPlayPiece(played, hand.getPiece(index)) &&
                            QwirkleRuleSet.canPlayPiece(board, rowsCopy, colsCopy, row, col))
                    {
                        board.setPiece(row,col,hand.removePiece(index));
                        view.setPlayerHighlight(current, index, null);
                        
                        rows.add(row);
                        cols.add(col);
                        view.update();
                        view.repaint();
                        return;
                    }
                }
            }
        }
    }
}
