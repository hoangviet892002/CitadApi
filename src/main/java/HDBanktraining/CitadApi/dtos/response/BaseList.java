package HDBanktraining.CitadApi.dtos.response;

public class BaseList <T>{
    private int total;
    private int page;
    private int size;
    private T data;
}
