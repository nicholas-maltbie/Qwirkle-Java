/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.EnumSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicholas
 * @param <State>
 */
abstract public class AbstractFSM<State extends Enum<State>>
{
    protected static Logger log = Logger.getLogger("AbstractFSM");
    
    private State currentState;
    private Map<State, EnumSet<State>> stateMap;
    private boolean isRunning;
    
    public AbstractFSM(Map<State, EnumSet<State>> stateMap, State inital)
    {
        currentState = inital;
        this.stateMap = stateMap;
    }
    
    public void start()
    {
        if(!isRunning)
        {
            isRunning = true;
            stateStarted(currentState);
        }
        else
            log.log(Level.SEVERE, "State Machine {0} has attempted to start but is already currently running", this);
    }
    
    public void stop()
    {
        if(isRunning)
        {
            isRunning = false;
            stateEnded(currentState);
        }
        else
            log.log(Level.SEVERE, "State Machine {0} has attempted to stop but is not currently running", this);
    }
    
    public State getState()
    {
        return currentState;
    }
    
    protected boolean isReachable(State state)
    {
        return stateMap.get(currentState).contains(state);
    }
    
    protected void setState(State state)
    {
        if(isReachable(state))
        {
            stateEnded(currentState);
            currentState = state;
            stateStarted(state);
        }
        else
            log.log(Level.SEVERE, "State {0} cannot be reached from State {1} and has not changed", new Object[]{state.toString(), currentState.toString()});
    }
    
    abstract protected void stateStarted(State state);
    abstract protected void stateEnded(State state);
}
