var bgLanguage;

var cookie = getCookie('language');

if(cookie === null) {
    document.cookie = "language = true";
    bgLanguage = true;
}else {
    if(cookie === 'true')
        bgLanguage = true;
    else
        bgLanguage = false;

}
function getCookie(name) {
    var dc = document.cookie;
    var prefix = name + "=";
    var begin = dc.indexOf("; " + prefix);
    if (begin == -1) {
        begin = dc.indexOf(prefix);
        if (begin != 0) return null;
    }
    else
    {
        begin += 2;
        var end = document.cookie.indexOf(";", begin);
        if (end == -1) {
            end = dc.length;
        }
    }
    // because unescape has been deprecated, replaced with decodeURI
    //return unescape(dc.substring(begin + prefix.length, end));
    return decodeURI(dc.substring(begin + prefix.length, end));
}

function setCookie() {
    var btn = document.getElementById('languageButtonChange');
    if(getCookie('language') == 'true') {
        document.cookie = "language = false";
        btn.src = 'https://res.cloudinary.com/dzqj0bike/image/upload/v1682530476/btu-guides/Flag_of_the_United_Kingdom__1-2_.svg_ayadam.webp';
    }
    else {
        document.cookie = "language = true";
        btn.src = 'https://res.cloudinary.com/dzqj0bike/image/upload/v1682530475/btu-guides/Flag_of_Bulgaria.svg_wldshm.png';
    }
}
