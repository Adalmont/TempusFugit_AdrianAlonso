/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function validarClave() {
    var clave = document.getElementById("j_idt17:clave").value;
    var confClave = document.getElementById("j_idt17:confClave").value;
    if (clave != confClave) {
        alert("Las contraseñas no coinciden");
        return false;
    } else {
        return true;
    }

}

