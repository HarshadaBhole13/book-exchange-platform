package Book_Exchange_Platform.book.exchange.Resources;

public class ExchangeRequestDTO {
    private Long senderId;
    private Long bookId;
    private String deliveryMethod;  // New field for delivery method
    private String duration;

    // Getters and setters
    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
