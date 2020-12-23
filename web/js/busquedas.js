
function busqueda(idselect) {

    $('#' + idselect).append($('<option>').text("Programa").attr('value', "null"));
    var params = {
        filtro: "programa"
    };

    $.get("/MainController.do", params, function (responseJson) {
        $.each(responseJson, function (index, programa) {
            $('#' + idselect).append($('<option>').text(programa.nombre).attr('value', programa.id));
        });
    });

}

function busqueda1(idselecP, idselectM) {
    document.getElementById(idselectM).innerHTML = "";
    $('#' + idselectM).append($('<option>').text("Materia").attr('value', "null"));

    var selectorvalue = document.getElementById(idselecP).value;
    var params = {
        filtro: "materia",
        programa: selectorvalue
    };

    $.get("/MainController.do", params, function (responseJson) {
        $.each(responseJson, function (index, materia) {
            $('#' + idselectM).append($('<option>').text(materia.nombre).attr('value', materia.id));
        });
    });
}

function buscar() {
    var titulo = document.getElementById("titulodoc").value;
    var selecPrograma = document.getElementById("seleccion_programa").value;
    var selectMateria = document.getElementById("seleccion_materias").value;

    var table = $('#table1').DataTable();
    table.clear().draw();

//    1. filtro by titulo
    if (titulo !== '' && selecPrograma === "null" && selectMateria === "null") {
        var params = {
            filtro: "buscar-titulo",
            titulo: titulo
        };
    }

//    2. filtro by titulo + programa
    if (titulo !== '' && selecPrograma !== "null" && selectMateria === "null") {
        var params = {
            filtro: "buscar-titulo-programa",
            titulo: titulo,
            programa: selecPrograma
        };
    }

//    3. filtro by programa
    if (titulo === '' && selecPrograma !== "null" && selectMateria === "null") {
        var params = {
            filtro: "buscar-programa",
            programa: selecPrograma
        };
    }

//    4. filtro by programa + materia
    if (titulo === '' && selecPrograma !== "null" && selectMateria !== "null") {
        var params = {
            filtro: "buscar-programa-materia",
            programa: selecPrograma,
            materia: selectMateria
        };
    }

//    5. filtro by titulo + programa + materia
    if (titulo !== '' && selecPrograma !== "null" && selectMateria !== "null") {
        var params = {
            filtro: "buscar-titulo-programa-materia",
            titulo: titulo,
            programa: selecPrograma,
            materia: selectMateria
        };
    }

//    solicitud al servlet
    $.get("MainController.do", params, function (responseJson) {
        var empty = true;
        $.each(responseJson, function (index, documento) {
            var programa = documento.programa;

            if (programa === "null")
                programa = "";

            var kb = parseInt(documento.size) / 1024;
            var url = documento.url;

            var rowData =
                    '<img src="' + documento.iconUrl + '">' +
                    '<strong><a target="blank" style="font-size:20px" href="' + url + '">' + documento.nombre + '</a></strong>' +
                    '<br><strong>Autor: </strong>' + documento.autor +
                    '<br><strong>Fecha Publicación: </strong>' + documento.fecha +
                    '<br><strong>Materia: </strong>' + documento.materia +
                    '<br><strong>Programa: </strong>' + programa +
                    '<br><strong>Tamaño: </strong>' + documento.size + " Kb";
            table.row.add([rowData]).draw(false);
            empty = false;
        });
        if (empty) {
            table.row.add(['Upps!... No hay resultados.']).draw(false);
        }
    });
}

function cargarSelect(index) {
    for (var i = 0; i < index; i++) {
        busqueda1("selectP", "selectM" + i);
    }

}

var parametros = null;

function drawInsert(params) {
    document.getElementById("sec-insert").innerHTML = "";
    var divmain = $('#sec-insert');
    divmain.addClass("card");

    var divcard = $('<div>').addClass("card-body");

    var divrowselectp = $('<div>').addClass("row");
    var selecp = '<select id="selectP" onchange="cargarSelect(' + params.length + ')"></select>';
    divrowselectp.append(selecp);
    divcard.append(divrowselectp);
    divmain.append(divcard);
    busqueda("selectP");

    for (var i = 0; i < params.length; i++) {
        var divrow = $('<div>').addClass("row");
        var divcolimg = $('<div>').addClass("col-md-auto");
        var divcoldata = $('<div>').addClass("col");
        var divselectM = $('<div>').addClass("col");

        divcolimg.append($('<img>').attr('src', params[i].iconUrl));
        divcoldata.append($('<a>').text(params[i].titulo).attr({href: params[i].url, target: "blank"}));

        divselectM.append($('<select>').attr('id', 'selectM' + i));

        divrow.append(divcolimg);
        divrow.append(divcoldata);
        divrow.append(divselectM);

        divcard.append(divrow);
    }

    var divrowbtn = $('<div>').addClass("row");
    var btn = '<button class="btn btn-secondary" onclick="insertDocument()">Guardar</button>';

    var br = $('<br>');
    divrowbtn.append(br);
    divrowbtn.append(br);
    divrowbtn.append(btn);
    divcard.append(divrowbtn);

    divmain.append(divcard);    
    parametros = params;
}

function insertDocument() {

    var programa = document.getElementById("selectP").value;

    if (programa === "null") {
        alert('Debe seleccionar un programa');
    } 
    else {
        for (var i = 0; i < parametros.length; i++) {
            var m = $('#selectM'+i).val();
            parametros[i].materia = m;
            parametros[i].programa = programa;
            $.get("/InsertController.do", parametros[i], function (responseJson) {
                $.each(responseJson, function (index, data) {
                    alert(data.msg);                    
                });
            });            
        }
        document.getElementById("sec-insert").innerHTML = "";        
        location.href = "/perfil";
        
    }


}

function filterYear(year) {
    var table = $('#table1').DataTable();
    table.search(year).draw();
}

function cargarDocumentos() {
    var table = $('#table2').DataTable();
    table.clear().draw();

    //    solicitud al servlet
    $.get("/DocumentosByUser.do", "", function (responseJson) {
        var empty = true;
        $.each(responseJson, function (index, documento) {
            var programa = documento.programa;

            if (programa === "null")
                programa = "";

            var kb = parseInt(documento.size) / 1024;
            var url = documento.url;

            var rowData =
                    '<img src="' + documento.iconUrl + '">' + 
                    '<strong><a target="blank" style="font-size:20px" href="' + url + '">' + documento.nombre + '</a></strong>' +
                    '<br><strong>Fecha Publicación: </strong>' + documento.fecha +
                    '<br><strong>Materia: </strong>' + documento.materia+
                    '<br><strong>Tamaño: </strong>' + documento.size + " Kb";
            table.row.add([rowData]).draw(false);
            empty = false;
        });
        if (empty) {
            table.row.add(['Upps!... No hay resultados.']).draw(false);
        }
    });

}






