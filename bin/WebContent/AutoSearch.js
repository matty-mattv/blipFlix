/*
 * This function is called by the library when it needs to lookup a query.
 * 
 * The parameter query is the query string.
 * The doneCallback is a callback function provided by the library, after you get the
 *   suggestion list from AJAX, you need to call this function to let the library know.
 */
function handleLookup(query, doneCallback) {
	console.log("autocomplete initiated")
	console.log("sending AJAX request to backend Java Servlet")
	
	// TODO: if you want to check past query results first, you can do it here
	
	var found = checkStorage(escape(query)); 
	
	if( ! found) {
		// sending the HTTP GET request to the Java Servlet endpoint hero-suggestion
		// with the query data
		jQuery.ajax({
			"method": "GET",
			// generate the request url from the query.
			// escape the query string to avoid errors caused by special characters 
			"url": "AutoSearch?query=" + escape(query),
			"success": function(data) {
				//pass the data to be stored in the browser for faster search 
				storeLookup(query, data); 
				// pass the data, query, and doneCallback function into the success handler			
				handleLookupAjaxSuccess(data, query, doneCallback) 					
			},
			"error": function(errorData) {
				console.log("lookup ajax error")
				console.log(errorData)
			}
		})
	}
	else {
		var data = getFromStorage(query); 
		handleLookupAjaxSuccess(data, query, doneCallback); 
	}
}

function checkStorage(query) {
	console.log("Check in storage"); 

	if( localStorage.getItem(query) === null ) {
		return false; 
	}
	else {
		return true; 
	}

}

function getFromStorage(query) {
	console.log("Get from storage"); 
	var data = localStorage.getItem(query);
	var parseData = JSON.parse( localStorage.getItem(query) ); 
	console.log("Test printing out data: " + parseData[0].value); 
	return data; 
}

function storeLookup(query, data) {
	console.log("Store in storage"); 
	var mData = ( data ); 
	
	localStorage.setItem(query, data); 	
	 	
}

/*
 * This function is used to handle the ajax success callback function.
 * It is called by our own code upon the success of the AJAX request
 * 
 * data is the JSON data string you get from your Java Servlet
 * 
 */
function handleLookupAjaxSuccess(data, query, doneCallback) {
	console.log("lookup ajax successful")
	
	// parse the string into JSON
	var jsonData = JSON.parse(data);
	
	console.log(jsonData)
	
	// call the callback function provided by the autocomplete library
	// add "{suggestions: jsonData}" to satisfy the library response format according to
	//   the "Response Format" section in documentation
	doneCallback( { suggestions: jsonData } );
}


/*
 * This function is the select suggestion hanlder function. 
 * When a suggestion is selected, this function is called by the library.
 * 
 * You can redirect to the page you want using the suggestion data.
 */
function handleSelectSuggestion(suggestion) {
	// TODO: jump to the specific result page based on the selected suggestion
	var category = suggestion["data"]["category"]; 
	var id = suggestion["data"]["id"]; 
	var titleOrName = suggestion["value"]; 
	
	var url = category +"?id=" + id; 
	console.log(url)
	document.location.href= url;
}


/*
 * This statement binds the autocomplete library with the input box element and 
 *   sets necessary parameters of the library.
 * 
 * The library documentation can be find here: 
 *   https://github.com/devbridge/jQuery-Autocomplete
 *   https://www.devbridge.com/sourcery/components/jquery-autocomplete/
 * 
 */
// $('#autocomplete') is to find element by the ID "autocomplete"
$('#autocomplete').autocomplete({
	// documentation of the lookup function can be found under the "Custom lookup function" section	
    lookup: function (query, doneCallback) {    		
    		handleLookup(query, doneCallback)
    },
    onSelect: function(suggestion) {
    		handleSelectSuggestion(suggestion)
    },
    // set the groupby name in the response json data field
    groupBy: "category",
    // set delay time
    deferRequestBy: 300,
    // there are some other parameters that you might want to use to satisfy all the requirements
    minChars: 3
});


/*
 * do normal full text search if no suggestion is selected 
 */
function handleNormalSearch(query) {
	console.log("doing normal search with query: " + query);
	// TODO: you should do normal search here
	
	var url = "Search?title=" + escape(query); 
	document.location.href= url;
}

// bind pressing enter key to a hanlder function
$('#autocomplete').keypress(function(event) {
	// keyCode 13 is the enter key
	if (event.keyCode == 13) {
		// pass the value of the input box to the hanlder function
		handleNormalSearch($('#autocomplete').val())
	}
})

// TODO: if you have a "search" button, you may want to bind the onClick event as well of that button

