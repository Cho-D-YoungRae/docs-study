package com.example.application.view.channel;

import com.example.application.chat.ChatService;
import com.example.application.chat.Message;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import reactor.core.Disposable;

import java.util.ArrayList;
import java.util.List;

@Route("/channel")  // 이 뷰를 /channel 경로로 접근할 수 있게 함
public class ChannelView
        extends VerticalLayout  // Vaadin 빌트인 레이아웃. 위에서부터 수직으로 컴포넌트 나열.
        implements HasUrlParameter<String> // URL 파라미터
{

    private final ChatService chatService;
    private final MessageList messageList;

    private final List<Message> receivedMessages = new ArrayList<>();

    private String channelId;

    public ChannelView(ChatService chatService) {
        this.chatService = chatService;
        setSizeFull();  // 뷰를 스크린 사이즈에 맞춤 -> width, height 100%

        this.messageList = new MessageList();   // 빌트인 컴포넌드. 메시지를 보여줌.
        this.messageList.setSizeFull();
        add(this.messageList);

        MessageInput messageInput = new MessageInput(); // 빌트인 컴포넌트. 메시지를 입력함
        messageInput.addSubmitListener(event -> sendMessage(event.getValue()));
        messageInput.setWidthFull();
        add(messageInput);
    }


    // HasUrlParameter 인터페이스의 메서드. URL 파라미터가 변경되면 호출
    @Override
    public void setParameter(BeforeEvent event, String channelId) {
        if (chatService.channel(channelId).isEmpty()) {
            throw new IllegalArgumentException("Invalid channel ID");
        }
        this.channelId = channelId;
    }

    private void sendMessage(String message) {
        if (!message.isBlank()) {
            chatService.postMessage(channelId, message);
        }
    }

    private MessageListItem createMessageListItem(Message message) {
        return new MessageListItem(
                message.message(),
                message.timestamp(),
                message.author()
        );
    }

    private void receiveMessages(List<Message> incoming) {  // 메시지를 배치 단위로 제공 -> 성능 향상
        // HTTP 요청 스레드가 아닌 다른 스레드에서 Vaadin UI를 업데이트할 때마다 UI.access()를 사용해야 함
        // 이 메서드는 업데이트가 진행되는 동안 세션이 제대로 잠겨 있는지 확인하고, 완료되면 변경 사항을 브라우저에 푸시
        getUI().ifPresent(ui -> ui.access(() -> {
            receivedMessages.addAll(incoming);
            messageList.setItems(receivedMessages.stream()
                    .map(this::createMessageListItem)
                    .toList());
        }));
    }

    private Disposable subscribe() {
        return chatService
                .liveMessages(channelId)
                .subscribe(this::receiveMessages);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        Disposable subscription = subscribe();
        addDetachListener(event -> subscription.dispose());
    }
}
