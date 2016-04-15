package at.aau.game.Mechanics.States;

import at.aau.game.Mechanics.Entities.GameObject;


public abstract class State {
    float stateTime;
    GameObject parentObject;
    float maxStateTime;
    boolean hasMaxStateTime;
    public boolean stackable;

    public State(float stateTime, GameObject parentObject, float maxStateTime, boolean stackable) {
        this.stateTime = stateTime;
        this.parentObject = parentObject;
        this.maxStateTime = maxStateTime;
        this.hasMaxStateTime = true;
        this.stackable = stackable;
    }

    public State(float stateTime, GameObject parentObject, boolean stackable) {
        this.stateTime = stateTime;
        this.parentObject = parentObject;
        this.hasMaxStateTime = false;
        this.stackable = stackable;
    }

    public void update(float delta){
        this.stateTime += delta;
        if(this.hasMaxStateTime){
            if(this.maxStateTime<this.stateTime){
                this.remove();
            }
        }
    }

    public void remove() {
        parentObject.removeState(this);
    }

    public void render(){

    }
}
