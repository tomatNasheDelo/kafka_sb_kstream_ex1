package ru.demo.kafka.producer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MessageRequest {
    @NotBlank(message = "Введите сообщение.")
    @Size(max = 1000, message = "Сообщение должно содержать не более 1000 символов.")
    private String message;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
