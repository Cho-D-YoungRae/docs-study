package com.example.application.view.lobby;

import com.example.application.chat.Channel;
import com.example.application.chat.ChatService;
import com.example.application.view.channel.ChannelView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;


@Route("/")
@PageTitle("Lobby")
public class LobbyView extends VerticalLayout {

    private final ChatService chatService;

    private final VirtualList<Channel> channels;
    private final TextField channelNameField;
    private final Button addChannelButton;

    public LobbyView(ChatService chatService) {
        this.chatService = chatService;
        setSizeFull();

        // VirtualList 는 스크롤 가능한 컨테이너로 내부에 아이템을 렌더링함
        // 아이템이 어떻게 렌더링될지를 컨트롤할 수 있음
        channels = new VirtualList<>();
        channels.setRenderer(new ComponentRenderer<>(this::createChannelComponent));
        add(channels);
        expand(channels);   // channels 컴포넌트가 가능한 공간을 모두 차지하도록 함

        channelNameField = new TextField();
        channelNameField.setPlaceholder("New channel name");

        addChannelButton = new Button("Add channel", event -> addChannel());
        addChannelButton.setDisableOnClick(true);   // 클릭하면 비활성화되도록 함 -> 여러번 클릭 방지

        HorizontalLayout toolbar = new HorizontalLayout(channelNameField, addChannelButton);
        toolbar.setWidthFull();
        toolbar.expand(channelNameField);
        add(toolbar);
    }

    private void refreshChannels() {
        channels.setItems(chatService.channels());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        refreshChannels();
    }

    private void addChannel() {
        try {
            String nameOfNewChannel = channelNameField.getValue();
            if (!nameOfNewChannel.isBlank()) {
                chatService.createChannel(nameOfNewChannel);
                channelNameField.clear();
                refreshChannels();
            }
        } finally {
            addChannelButton.setEnabled(true);
        }
    }

    private Component createChannelComponent(Channel channel) {
        return new RouterLink(channel.name(), ChannelView.class, channel.id());
    }
}
