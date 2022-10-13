function signup() {
    let data = {
        email: $('.signup-email').val(),
        password: $('.signup-password').val(),
        nickname: $('.signup-nickname').val(),

    };

    document.addEventListener("submit", function(e) {
        e.preventDefault();
    })


    $.ajax({
        type: "POST",
        url: "/sign-up",
        data: JSON.stringify(data),
        dataType: "text",
        contentType: 'application/json',
    }).done(function() {
        alert("회원 가입 완료");
        window.location.href = '/';
    }).fail(function(error) {

        alert(JSON.stringify(error));

    });
}