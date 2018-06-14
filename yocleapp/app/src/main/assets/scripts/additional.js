var cookname = "amem12345"; 

 function LoginSuccessful(username) {
			document.getElementById("signOut2").style.display = "inline";
			document.getElementById("signIn2").style.display = "none";

			var obj = document.getElementById("become_member");
			if(obj) {
				obj.style.display = "none";
			}
			
			document.getElementById("ss").innerHTML = "像傳奇一樣投資 (歡迎 "+username+")";
		}
			
			
			
			
		function getCookie(cname) {
				var name = cname + "=";
				var ca = document.cookie.split(';');
				for(var i=0; i<ca.length; i++) {
						var c = ca[i];
						while (c.charAt(0)==' ') c = c.substring(1);
						if (c.indexOf(name) == 0) return unescape(c.substring(name.length,c.length));
				}
				return "";
		}			
		
		function setCookie(cname, cvalue, time) {
			cvalue = escape(cvalue);
			document.cookie = cname + "=" + cvalue + "; expires="+time+"; path=/";
		}

		function newUser() {
			$.fancybox.close();
			$(".register a").trigger('click');
		}
