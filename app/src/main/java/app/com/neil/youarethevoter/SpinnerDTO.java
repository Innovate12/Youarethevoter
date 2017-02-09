package app.com.neil.youarethevoter;

/**
 * Created by swapnilparashar on 11/9/2016.
 */

    public class SpinnerDTO {
        private int id;
        private String name;

        public SpinnerDTO(){
            this.id = 0;
            this.name = "";
        }

        public void setId(int id){
            this.id = id;
        }

        public int getId(){
            return this.id;
        }

        public void setName(String name){
            this.name = name;
        }

        public String getName(){
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }

    }
