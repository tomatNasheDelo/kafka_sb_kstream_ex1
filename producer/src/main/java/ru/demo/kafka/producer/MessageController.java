package ru.demo.kafka.producer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {
    private final MessagePublisher messagePublisher;

    public MessageController(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("messageRequest", new MessageRequest());
        return "index";
    }

    @PostMapping("/messages")
    public String sendMessage(
            @Valid @ModelAttribute("messageRequest") MessageRequest request,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        messagePublisher.publish(request.getMessage());
        model.addAttribute("message", request.getMessage());
        return "sent";
    }
}
