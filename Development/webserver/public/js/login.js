function enterpress(e){
    if(e.keyCode == 13){
        login();
    }
}
function enterpressReg(e){
    if(e.keyCode == 13){
        register();
    }
}

function login(){
	var mail = $('#mail').val();
	var pass = $('#password').val();
	if(mail != "" && pass != ""){
		$.post('/login', {
			mail: mail,
			pass: pass
		}, function(ans){
			if(ans.error) $('#error').text(ans.error);
			else {
				if(ans.admin) window.location.href = '/admin';
				else window.location.reload();
			}
		}).fail(function(err){
			$('#error').text(JSON.parse(err.responseText).error);
		});
	} else $('#error').text('Det saknas värden!');
}

function register() {
	var mail = $('#reg_mail').val();
	var pass = $('#reg_password').val();
	var repass = $('#reg_repassword').val();

	if(pass == repass){
		if(mail != "" && pass != ""){
			$.post('/login/register', {
				mail: mail,
				pass: pass
			}, function(ans){
				if(ans.error) $('#error').text(ans.error);
				else {
					showSuccess("Ditt konto har skapats se mail för att godkänna");
				}
			}).fail(function(err){
        console.log(err);
				$('#error').text(err.responseText);
			});
		} else $('#error').text('Det saknas värden!');
	} else {
		$('#error').text('Lösenorden matchar inte varandra');
	}
}
