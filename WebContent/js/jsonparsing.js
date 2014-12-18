/**
 * 
 */

$('#betSubmitButton').click(function testingfn(){
alert('Testing');
   
   var level = $('#riskLevel').val();
   var amount = $('#amount').val();
   var urlString = 'ManageBets?level='+level+'&amount='+amount;
   
  var ajaxCall = $.ajax({
       url: urlString, //sending url string with parameters....sending request
   cache: false,
   dataType: 'application/json',
   success: function(response) //obtaining response
   {
	   var x = response.responseText;
       alert('Success : ' + x);
  //updatingContent(event);
   },
   complete: function(response) 
   {
	   var x = response.responseText;
       alert('Success : ' + x);
   alert('Complete : ' + x);
       }
       
       });
  
   });
					              