/* get tweaked URLs */
function onLoadFunction() {
    var origURL = document.URL;
    /* building links dynamically */
    var startCommandInd = origURL.indexOf("interface?");
    var endCommandInd = origURL.length;
    var command = origURL.slice(startCommandInd, endCommandInd);
    var hostName = origURL;
    var homeURL = hostName;
    var element;

    // get repo value
    var repo = getParameterValueFromURL("repo", origURL);
    // get user value
    var user = getParameterValueFromURL("user", origURL);
    // get publish value
    var publish = getParameterValueFromURL("publish", origURL);
    // get trans value
    var trans = getParameterValueFromURL("trans", origURL);

    // update form accordingly
    if (document.getElementById("repo") != null)
        document.getElementById("repo").value = repo;
    if (document.getElementById("user") != null)
        document.getElementById("user").value = user;
    if (document.getElementById("publish") != null) {
        if (publish != "")
            document.getElementById("publish").value = publish;
        else
            document.getElementById("publish").value = "";
    }


    if (startCommandInd != -1) {
        hostName = origURL.slice(0, startCommandInd);
        homeURL = hostName + "index.html";

        var newJavaURL = hostName + command.replace("trans=" + trans, "trans=java");
        element = document.getElementById("java_url");
        if (newJavaURL != null && element != null)
            element.href = newJavaURL;

        var newXMLURL = hostName + command.replace("trans=" + trans, "trans=raw");
        element = document.getElementById("xml_url");
        if (newXMLURL != null && element != null)
            element.href = newXMLURL;

        var newHTMLURL = hostName + command.replace("trans=" + trans, "trans=html");
        element = document.getElementById("html_url");
        if (newHTMLURL != null && element != null)
            element.href = newHTMLURL;
    }

    element = document.getElementById("home_url");
    if (homeURL != null && element != null)
        element.href = homeURL;
}

function getParameterValueFromURL(param, origURL) {
    var startCommandInd = origURL.indexOf(param + "=");
    if (startCommandInd != -1) {
        startCommandInd = startCommandInd + param.length + 1;
        var value = origURL.slice(startCommandInd, origURL.length);
        var endCommandInd = value.indexOf("&"); // returns -1 if character & never occurs
        if (endCommandInd != -1)
            value = value.slice(0, endCommandInd);
    } else
        value = "";

    return value
}

function removeEmptyGetParameters() {
    var myForm = document.getElementById("searchForm");

    if (myForm != null) {
        // remove empty inputs
        var allInputTags = myForm.getElementsByTagName("input");
        var input, i;
        for (i = 0; input = allInputTags[i]; i++) {
            if (input.getAttribute("name") && !input.value) {
                input.setAttribute("name", "");
            }
        }

        // remove empty select
        var selectTag = document.getElementById("publish");
        var publishValue = selectTag.options[selectTag.selectedIndex].text;
        if (!selectTag.value || publishValue == "Both") {
            selectTag.removeAttribute("name");
        }
    }
}