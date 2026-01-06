package StudentManagement.StudentManagement.exception;

public class UserAlreadyExistException extends  RuntimeException{
    public UserAlreadyExistException(){
        super("User Already Present");
    }
}
