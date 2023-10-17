function convertText(){
    let textToConvertJson = JSON.stringify({
        "textToConvert": $("#textToConvert").val()
    });

    $.ajax({
        url: "http://www.eduardo.soares.nom.br:8081/convert",
        type: "post",
        data: textToConvertJson,
        contentType: "application/json",

        success: function(response){
            $("#convertedText").text(response.textConverted);
            copyText();
        },

        error: function(error){
            if(error.status>=400 && error.status <=499){
                let problem = JSON.parse(error.responseText);
                alert(problem.userMessage);
            }else{
                alert("Erro de sistema ao fazer a conversão");
            }
        }
    });

}

function copyText(){
    let copyText = document.getElementById("convertedText");

    copyText.focus();
    copyText.select()
    copyText.setSelectionRange(0,99999);

    document.execCommand('copy');

}

