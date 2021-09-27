package me.lexi.DiscordBot.listeners.impl;

import me.lexi.DiscordBot.listeners.RateListener;
import me.lexi.DiscordBot.services.MessagingService;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class RateListenerImpl implements RateListener {
    private final static Pattern pattern = Pattern.compile("!rate (\\w+)");

    @Autowired
    private MessagingService messagingService;

    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if(messageCreateEvent.getMessageContent().startsWith("!rate")) {
            Matcher matcher = pattern.matcher(messageCreateEvent.getMessageContent());
            if (matcher.matches()) {
                //Do the rating
                int rating = (int) Math.floor(Math.random() * 100) + 1;
                messagingService.sendMessage(messageCreateEvent.getMessageAuthor(),
                "Rate calculator",
                        messageCreateEvent.getMessageAuthor().getDisplayName() + " is " + rating+  "% " + matcher.group(1),
                        "Rate again?",
                        "https://media.discordapp.net/attachments/754193613591805982/892118299490541568/1632761886379.png?width=691&height=676",
                        messageCreateEvent.getChannel());
            } else {
                // Send a helpful syntax message
                messagingService.sendMessage(messageCreateEvent.getMessageAuthor(),
                        "Rate calculator",
                        "Are you trying to use the 'rate' command? Please use the syntax '!rate [word]'. Thanks!",
                        "Rate again?",
                        null,
                        messageCreateEvent.getChannel());
            }
        }
    }
}