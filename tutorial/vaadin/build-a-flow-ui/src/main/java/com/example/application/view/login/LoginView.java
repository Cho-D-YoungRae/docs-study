package com.example.application.view.login;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("/login")
@PageTitle("Chat Login")
@AnonymousAllowed   // security 활성화 되면 모든 뷰 거절되는 것이 디폴트. 로그인 뷰는 접근되어야 하므로
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm;

    public LoginView() {
        loginForm = new LoginForm();
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");   // 로그인 버튼 누르면, POST /login 요청

        add(
                new H1("Vaadin Chat"),
                new Div("You can log in as 'alice', 'bob' or 'admin'. The password for all of them is 'password.'"),
                loginForm
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // error 쿼리파라 미터 있을 경우 (/login?error), 로그인폼은 에러 메세지 표출
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
