function login() {
    let data = {
        email: $('.login-email').val(),
        password: $('.login-password').val(),
    };

    document.addEventListener("submit", function(e) {
        e.preventDefault();
    })

    $.ajax({
        type: "POST",
        url: "/login",
        data: JSON.stringify(data),
        dataType: "text",
        contentType: 'application/json',
    }).done(function() {
        alert("feed로 이동");
        window.location.href = '/feed';
    }).fail(function(error) {

        alert(JSON.stringify(error));

    });
}