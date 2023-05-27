package UIException;

public class RotorAlreadyExist extends RuntimeException{

        private int rotorId;
        private final String EXCEPTION_MESSAGE="You already selected rotor id %d!!! ";
        public RotorAlreadyExist(int rotorId){
            this.rotorId=rotorId;
        }
        @Override
        public String getMessage(){
            return String.format(EXCEPTION_MESSAGE,rotorId);
        }
    }

