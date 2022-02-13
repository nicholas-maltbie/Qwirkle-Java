package controller;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Player;
import model.QwirkleGame;
import view.QwirkleView;

public class QwirkleMachine extends AbstractFSM<controller.QwirkleMachine.GameState>
{
    public static enum GameState {SETUP, PLAYER_TURN, END}
   
    public static final Map<GameState, EnumSet<GameState>> stateMap;
    
    static {
        stateMap = new HashMap<>();
        
        stateMap.put(GameState.SETUP, EnumSet.of(GameState.PLAYER_TURN));
        stateMap.put(GameState.PLAYER_TURN, EnumSet.of(GameState.PLAYER_TURN, GameState.END));
        stateMap.put(GameState.END, EnumSet.of(GameState.SETUP));
    }
    
    private QwirkleView view;
    private QwirkleGame game;
    
    public QwirkleMachine() 
    {
        super(stateMap, GameState.SETUP);
    }
    
    @Override
    protected void stateStarted(GameState state) 
    {
        switch(state)
        {
            case SETUP:
                
                Player player1 = new Player("Player", false);
                Player player2 = new Player("Computer", true);
                int startPlayer = (int)(Math.random()*2);
                game = new QwirkleGame(player1, player2, startPlayer);
                view = new QwirkleView(game);
                view.setVisible(true);
                view.setBounds(100,100,700,700);
                view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                view.repaint();
                String name = null;
                while(name == null || name.trim().isEmpty() || name.length() <= 3 || name.length() >= 15)
                    name = (String)JOptionPane.showInputDialog(
                        view,
                        "What's your name? (3 to 15 characters)",
                        "Game Start",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        "Player");
                game.getPlayer(0).setName(name);
                view.repaint();
                JOptionPane.showMessageDialog(view,
                    game.getPlayer(startPlayer).getName() + " plays first");
                this.setState(GameState.PLAYER_TURN);
                break;
            case PLAYER_TURN:
                Player current = game.getPlayer(game.getCurrentPlayer());
                if(QwirkleRuleSet.canPlayerPlay(game, current))
                {
                    if(!current.isComputer())
                    {
                        view.setStatus(game.getCurrentPlayer(), "Taking turn...");
                        view.repaint();
                        PlayerTurn turn = new PlayerTurn(game, view, this, game.getCurrentPlayer());
                        turn.start();
                    }
                    else
                    {
                        view.setStatus(game.getCurrentPlayer(), "Thinking...");
                        view.update();
                        view.repaint();
                        //System.out.println(current.getName() + " is thinking");
                        Computer.makeMove(game.getBoard(), game.getDeck(), current, view.getBoardView());
                        String status = "Done with turn";
                        view.setStatus(game.getCurrentPlayer(), status);
                        view.update();
                        view.repaint();
                        //System.out.println(current.getName() + " has made a move");
                        finishTurn(null);
                    }
                }
                else if(QwirkleRuleSet.isGameOver(game))
                    this.setState(GameState.END);
                else
                    finishTurn(null);
                break;
            case END:
                view.update();
                view.repaint();
                String line = "Player 1 has won with a score of " + game.getPlayer(0).getScore() + ",\n";
                if(game.getPlayer(1).getScore() > game.getPlayer(0).getScore())
                    line = "Player 2 has won with a score of " + game.getPlayer(1).getScore() + ",\n";
                else if(game.getPlayer(1).getScore() == game.getPlayer(0).getScore())
                    line = "It was a tie at the score of " + game.getPlayer(0).getScore() + ",\n";
                JFrame frame = new JFrame();
                boolean again = JOptionPane.showConfirmDialog(
                        frame,
                        line + "would you like to play again?",
                        "Play Again?",
                        JOptionPane.YES_NO_OPTION) == 0;
                view.dispose();
                if(again)
                    this.setState(GameState.SETUP);
                else
                {
                    this.stop();
                    System.exit(0);
                }
                break;
        }
    }
    
    @Override
    protected void stateEnded(GameState state) 
    {
        
    }
    
    public void finishTurn(final PlayerTurn turn)
    {
        view.setStatus(game.getCurrentPlayer(), "Finished turn");
        game.nextPlayer();
        Runnable nextTurn = new Runnable(){
            @Override
            public void run()
            {
                if(turn != null)
                    turn.unregister();
                setState(GameState.PLAYER_TURN);            
            }
        };
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.schedule(nextTurn, 1, TimeUnit.MILLISECONDS);
    }
    
}
