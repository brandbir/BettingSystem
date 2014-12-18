
var ajaxCall;

function validateLogin()
{
   if( document.loginForm.username.value == "" )
   {
     document.loginForm.username.focus() ;
     return false;
   }
   if( document.loginForm.password.value == "" )
   {
     document.loginForm.password.focus() ;
     return false;
   }
   return( true );
};

function receiveJSONManageBets()
{
	alert('accessing Manage Bets fn');
	
	var level = $('#riskLevel').val();
	var amount = $('#amount').val();
	var urlString = '/ManageBets?level='+level+'&amount='+amount;
	
	ajaxCall = $.ajax({
		url: urlString, //sending url string with parameters....sending request
		cache: false,
		dataType: 'json',
		success: function(event) //obtaining response
		{
			updatingContent(event);
		},
		complete: function(event) 
		{
			alert('Complete : ' + event.data);
		}
	});
}

function receiveJSONManageBets()
{
	ajaxCall = $.ajax({
		url: '/UserSubmission',
		cache: false,
		dataType: 'text',
		success: function(event)
		{
			alert('Success : ' + event.data);
			updatingContent(event);
		},
		complete: function(event) 
		{
			alert('Complete : ' + event.data);
		}
	});
}


function updatingContent(data)
{
	var json = data;
	var json_parsed = JSON.parse(json);
	
	
	
	//examples json_parse.TIMESTAMP
}
	
