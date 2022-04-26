var container = document.getElementById('container');
var slider = document.getElementById('slider');
var containerDiv=[...document.getElementsByClassName('container')][0];
var cards=[...document.getElementsByClassName('card')];
var cards2=[...document.getElementsByClassName('card2')];
var fsSlides=[...document.getElementsByClassName('slide')];
var ssSlides=[...document.getElementsByClassName('slide2')];
var slides = document.getElementsByClassName('slide').length;
var buttons = document.getElementsByClassName('btn');

var page='first';
var btn=document.getElementById('change-button');

var currentPosition = 0;

var currentMargin = 0;
var slidesPerPage = 0;
var times=0;
var slidesCount = slides - slidesPerPage;
var containerWidth = container.offsetWidth;
var prevKeyActive = false;
var nextKeyActive = true;

window.addEventListener("resize", checkWidth);

function checkWidth() {
    containerWidth = container.offsetWidth;
    setParams(containerWidth);
}

function setParams(w) {
    if (w < 551) {
        slidesPerPage = 1;
    } else {
        if (w < 901) {
            slidesPerPage = 2;
        } else {
            if (w < 1101) {
                slidesPerPage = 3;
            } else {
                slidesPerPage = 4;
            }
        }
    }
    slidesCount = slides - slidesPerPage;
    if (currentPosition > slidesCount) {
        currentPosition -= slidesPerPage;
    };
    currentMargin = - currentPosition * (100 / slidesPerPage);
    slider.style.marginLeft = currentMargin + '%';
    if (currentPosition > 0) {
        buttons[0].classList.remove('inactive');
    }
    if (currentPosition < slidesCount) {
        buttons[1].classList.remove('inactive');
    }
    if (currentPosition >= slidesCount) {
        buttons[1].classList.add('inactive');
    }
}

setParams();

function slideRight() {
    if (currentPosition != 0) {
        slider.style.marginLeft = currentMargin + (100 / slidesPerPage) + '%';
        currentMargin += (100 / slidesPerPage);
        currentPosition--;
    };
    if (currentPosition === 0) {
        buttons[0].classList.add('inactive');
    }
    if (currentPosition < slidesCount) {
        buttons[1].classList.remove('inactive');
    }
};

function slideLeft() {
    if (currentPosition != slidesCount) {
        slider.style.marginLeft = currentMargin - (100 / slidesPerPage) + '%';
        currentMargin -= (100 / slidesPerPage);
        currentPosition++;
    };
    if (currentPosition == slidesCount) {
        buttons[1].classList.add('inactive');
    }
    if (currentPosition > 0) {
        buttons[0].classList.remove('inactive');
    }
};





// Slider transition

containerDiv.addEventListener('mouseover',e => {
    cards.forEach(c => {
        if(times===0){
        c.classList.add('height-hundred');
        c.childNodes[1].childNodes[1].classList.add('scale-one');
        setTimeout(function () {
            c.childNodes[3].style.opacity=1;
        },400)
        }
    });
    times++;
});


function changeSlider() {
      cards=[...document.getElementsByClassName('card')];
      cards2=[...document.getElementsByClassName('card2')];
      fsSlides=[...document.getElementsByClassName('slide')];
      ssSlides=[...document.getElementsByClassName('slide2')];


       setTimeout(function () {
            container.classList.add('hidden');
            document.getElementById('container2').id='container'; 
            container.id='container2';
             container=document.getElementById('container');
             document.getElementById('slider2').id='slider';
             slider.id='slider2';
             slider=document.getElementById('slider');
       },500)
    cards.forEach(c => {
            c.childNodes[3].classList.add('hidden');
            c.childNodes[3].style.opacity=0;
           c.childNodes[1].childNodes[1].classList.add('animatedImg');
            if(c.classList.contains('height-hundred')){
                c.classList.remove('height-hundred');
            }
           setTimeout(function() {
            c.classList.remove('card');
            c.classList.add('card2');
           },600);
        });
    setTimeout(function() {
        container.classList.remove('hidden');
        slides = document.getElementsByClassName('slide').length;
        fsSlides.forEach(s => {
            s.classList.remove('slide');
            s.classList.add('slide2');
        })
        buttons = document.getElementsByClassName('btn');
         container = document.getElementById('container')
         slider = document.getElementById('slider');
         slides = document.getElementsByClassName('slide').length;
         buttons = document.getElementsByClassName('btn');
         currentPosition = 0;
         currentMargin = 0;
         containerWidth = container.offsetWidth;
        slides=document.getElementsByClassName('slide').length;
        console.log(slides);
        slidesCount = slides - slidesPerPage;
        prevKeyActive = false;
        nextKeyActive = true;
    },510);
    ssSlides.forEach(s => {
        s.classList.remove('slide2');
        s.classList.add('slide');
    })
    cards2.forEach(c => {
        c.classList.remove('card2');
        c.classList.add('card');
        c.childNodes[1].childNodes[1].classList.remove('animatedImg');
        if( c.childNodes[1].childNodes[1].classList.contains('scale-one')) {
            c.childNodes[1].childNodes[1].classList.remove('scale-one');
        }
        setTimeout(function () {
             c.classList.add('height-hundred');
              c.childNodes[1].childNodes[1].classList.add('scale-one')
             c.childNodes[3].classList.remove('hidden');
            },600)
            setTimeout(function () {
                c.childNodes[3].style.opacity=1;
            },1000)
    })
}