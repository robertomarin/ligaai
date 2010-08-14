
$.ajaxSetup({ scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"});
var encodeUrl = escape;
if(encodeURIComponent) {
	encodeUrl = encodeURIComponent;
}

$(function() {
	$('#microurl').submit(function() {
		if($.trim($('#url').val()) != ''){
			$('#microurlmicro').removeClass('copiedSuccessfully');
			$('#clickToCopy').html('Clique para copiar');
			var url = '/ajax/microurl?url=' + encodeUrl($('#url').val());
			$('#loader').show();
			$.getJSON(url, function(data) {
				if(data.microurl.url){
					$('#loader, .message').hide();
					var microurl = document.location.protocol + '//' + document.location.hostname + (document.location.port ? ':' + document.location.port : '') + '/' + data.microurl.micro;
					
					$('#doneShortenUrl').fadeIn();
					$('#microurlmicro').val(microurl);
					$('#twitter').attr('href', 'http://twitter.com/timeline/home?status=' + microurl);
					
					var savedChars = $('#url').val().length - microurl.length;
					
					if(savedChars <= 0){
						$('#savedChars').html('nenhum :(');
					} else{
						$('#savedChars').html(savedChars);
					}

					//Configurando copy to clipboard
			        var clipComplete = function(client){
			        	$('#microurlmicro').addClass('copiedSuccessfully');
			        	$('#clickToCopy').html('URL Copiada');
			        };

					ZeroClipboard.setMoviePath('/js/ZeroClipboard.swf');
					var clip = new ZeroClipboard.Client();
					clip.addEventListener('onComplete', clipComplete);
					clip.setText(microurl);
					clip.glue('microurlmicro');
				}else {
					$('.message').html('Ocorreu um erro na hora de encurtar :(').fadeIn();
				}
				$('#microurlurl').html('<a href="' + data.microurl.url + '" target="_blank">' + data.microurl.url + '</a>');
			});
		}else{
			$('.message').fadeIn();
		}
		return false;
	});
	
	
	$('#ligaai').submit(function() {
		var url = '/ajax/ligaai?'
			+ 'message=' + encodeUrl($('#message').val())
			+ '&contact=' + encodeUrl($('#contact').val())
			+ '&email=' + encodeUrl($('#email').val())
			+ '&contactType=' + encodeUrl($('.contactType').val());
		
		
		
		$.getJSON(url, function(data) {
			if(data.ok) {
				//var microurl = document.location.protocol + '//' + document.location.hostname + '/' + data.microurl.micro;
				$('#content article:first').prepend('<article><div class="userPic">foto</div><div class="userInfo"><header><hgroup><h1>Nome do rebento</h1><h2>' + $('#contact').val() + '</h2></hgroup></header></div></article>');
			} else {
				alert('bug?');
			}
		});
		
		return false;
	});
	
	 /*!
	  * About
	 */
	 
	 $('#aboutText').click(function(){
		$('#aboutContent').slideToggle('slow');
	 });
	 
	 /*!
	  * Input clone
	 */

	 /*$('.contactInfo').focus(function(){
		$('#cloneable').clone(true).removeAttr("id").fadeIn(350).insertAfter($(this).parent());
		$(this).unbind('focus');
	 });*/
	 
	 /*!
	  * Input masking
	 */
	 //$('.phone').mask('(99) 9999-9999');
	 
	 $('.contactType').each(function(){
		 $(this).change(function(){
			 if($(this).val() == 'PHONE') {
				 $(this).parent().find('.contactInfo').mask('(99) 9999-9999'); 
			  } else{
				 $(this).parent().find('.contactInfo').unmask(); 
			  };
		 });
	 });
	 
	 /**Subscribe dialog**/
	 $('#subscribe').dialog({
		autoOpen: false,
		modal: true,
		width: 445,
		height: 'auto',
		closeText: '[x]'
	 });
	 
	 $('#subscribeButton').click(function(){$('#subscribe').dialog('open');});
});