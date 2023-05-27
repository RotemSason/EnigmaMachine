package UIException;

public class WrongInputNum extends RuntimeException{
    private int choice;
    private final String EXCEPTION_MESSAGE="You choose %d and this is not one of the options in the menu,please choose a number between 1-8";
    public WrongInputNum(int choice){
      this.choice=choice;
    }
    @Override
    public String getMessage(){
        return String.format(EXCEPTION_MESSAGE,choice);
    }
}
