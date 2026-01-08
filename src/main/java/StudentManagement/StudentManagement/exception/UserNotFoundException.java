package StudentManagement.StudentManagement.exception;

public class UserNotFoundException  extends RuntimeException{
    public UserNotFoundException(){
        super("student not found with this id");
    }
}
