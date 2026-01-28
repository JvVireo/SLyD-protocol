public class IllegalSLyDFormatExpertion extends Exception{
    public IllegalSLyDFormatExpertion(Throwable cause, String message){
        super(message, cause);
    }
    public IllegalSLyDFormatExpertion(String message){
        this(null,message);
    }
    public IllegalSLyDFormatExpertion(Throwable cause){
        this(cause,null);
    }
    public IllegalSLyDFormatExpertion(){
        this((String) null);
    }
}