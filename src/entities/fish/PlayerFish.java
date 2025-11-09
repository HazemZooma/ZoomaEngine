package entities.fish;

public class PlayerFish extends Fish {

    public PlayerFish(Builder builder){
     super(builder);
    }


    public static class Builder extends Fish.Builder<Builder>{

       public Builder(float width , float height){
           super(width , height);
       }

        @Override
        protected Builder self() {
            return this;
        }

        public PlayerFish build(){
            return new PlayerFish(this);
        }
    }
}
