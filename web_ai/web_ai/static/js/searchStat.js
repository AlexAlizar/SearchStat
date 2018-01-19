document.getElementById('slider-next').onclick = sliderNext;
var next = 0;

function sliderNext() {
    var line = document.getElementById('line');
    next = next - 250;
    if (next < -2250) {
        next = 0;
    }
    line.style.left = next + 'px';

}



//  var modalWindow = {
//    _block: null,
//    _win: null,
//    initBlock: function() {
//      _block = document.getElementById('blockscreen'); //Получаем наш блокирующий фон по ID
//
//      //Если он не определен, то создадим его
//      if (!_block) {
//        var parent = document.getElementsByTagName('body')[0]; //Получим первый элемент тега body
//        var obj = parent.firstChild; //Для того, чтобы вставить наш блокирующий фон в самое начало тега body
//        _block = document.createElement('div'); //Создаем элемент div
//        _block.id = 'blockscreen'; //Присваиваем ему наш ID
//        parent.insertBefore(_block, obj); //Вставляем в начало
//        _block.onclick = function() {
//            modalWindow.close();
//          } //Добавим обработчик события по нажатию на блокирующий экран - закрыть модальное окно.
//      }
//      _block.style.display = 'inline'; //Установим CSS-свойство   
//
//    },
//
//    initWin: function() {
//      _win = document.getElementById('modalwindow'); //Получаем наше диалоговое окно по ID
//      //Если оно не определено, то также создадим его по аналогии
//      if (!_win) {
//        var parent = document.getElementsByTagName('body')[0];
//        var obj = parent.firstChild;
//        _win = document.createElement('div');
//        _win.id = 'modalwindow';
//        _win.style.padding = '0 0 5px 0';
//        parent.insertBefore(_win, obj);
//      }
//      // _win.style.width = "400px"; //Установим ширину окна
//      // _win.style.height = "300px"; //Установим ширину окна
//      _win.style.display = 'inline'; //Зададим CSS-свойство
//
//      _win.innerHTML = '<div id="modal-window--wrapper"><form id="feedback-form"><label for="name">Ваше имя:</label><input type="text" id="user-name" name="name"><br><label for="email">Ваш e-mail:</label><input type="email" id="user-email" name="email"><br><label for="password">Ваш пароль:</label><input type="password" id="user-password" name="password"><br><label for="password">Повторите:</label><input type="password" id="user-password-rep" name="password"></form><input type="button" id="modal-cancel" onclick="modalWindow.close()" value="Отмена"><input type="button" id="modal-submit" value= "Отправить"></div>'
//      //Установим позицию по центру экрана
//
//      _win.style.left = '50%'; //Позиция по горизонтали
//      _win.style.top = '50%'; //Позиция по вертикали
//
//      //Выравнивание по центру путем задания отрицательных отступов
//      _win.style.marginTop = -(300 / 2) + 'px';
//      _win.style.marginLeft = -(400 / 2) + 'px';
//    },
//
//    close: function() {
//      document.getElementById('blockscreen').style.display = 'none';
//      document.getElementById('modalwindow').style.display = 'none';
//    },
//    show: function() {
//      modalWindow.initBlock();
//      modalWindow.initWin();
//    }
//  }
//  



var modalWindow = {
    _block: null,
    _winReg: null,
    _winForgotPass: null,
    _winFeedBack: null,

    initBlock: function () {
        _block = document.getElementById('blockscreen'); //Получаем наш блокирующий фон по ID

        //Если он не определен, то создадим его
        if (!_block) {
            var parent = document.getElementsByTagName('body')[0]; //Получим первый элемент тега body
            var obj = parent.firstChild; //Для того, чтобы вставить наш блокирующий фон в самое начало тега body
            _block = document.createElement('div'); //Создаем элемент div
            _block.id = 'blockscreen'; //Присваиваем ему наш ID
            parent.insertBefore(_block, obj); //Вставляем в начало
            _block.onclick = function () {
                modalWindow.close();
            } //Добавим обработчик события по нажатию на блокирующий экран - закрыть модальное окно.
        }
        _block.style.display = 'inline'; //Установим CSS-свойство   

    },

    initWinReg: function () {
        _winReg = document.getElementById('modalwindow'); //Получаем наше диалоговое окно по ID
        //Если оно не определено, то также создадим его по аналогии
        if (!_winReg) {
            var parent = document.getElementsByTagName('body')[0];
            var obj = parent.firstChild;
            _winReg = document.createElement('div');
            _winReg.id = 'modalwindow';
            _winReg.style.padding = '0 0 5px 0';
            parent.insertBefore(_winReg, obj);
        }
        _winReg.style.width = "400px"; //Установим ширину окна
        _winReg.style.height = "300px"; //Установим ширину окна
        _winReg.style.display = 'inline'; //Зададим CSS-свойство
        
        
// тестовая форма на отправку регистрации
// она создается каждый раз при нажатие кнопки, потом она же и вы зывается, это позволяет экономить вермя и память.
// тут я прописал в форме и метод добавил атрибут action. Вообще, это должно быть все в функции ниже. Но у меня получился такой франкинштейн, что...
    // В общем, скорее всего надо сюда добавить твой файл и метод, как мне кажется, упаковывается все в JSON
        
        _winReg.innerHTML = '<div id="modal-window--wrapper">' +
            '<form class="feedback-form" id="test  action="#" method="POST">' +
            '<label for="name">Ваше имя:</label>' +
            '<input type="text" class="user-name" name="name">' +
            '<br>' +
            '<label for="email">Ваш e-mail:</label>' +
            '<input type="email" class="user-email" name="email">' +
            '<br>' +
            '<label for="password">Ваш пароль:</label>' +
            '<input type="password" class="user-password" name="password">' +
            '<br>' +
            '<label for="password">Повторите:</label>' +
            '<input type="password" class="user-password-rep" name="password">' +
            '<div class="modal-button--wrapper"><input type="button" class="modal-cancel" onclick="modalWindow.close()" value="Отмена">' +
            '<input type="submit" class="modal-submit" value="Отправить">' +
            '</div>' +
            '</form>' +
            '</div>'
        //Установим позицию по центру экрана

        _winReg.style.left = '50%'; //Позиция по горизонтали
        _winReg.style.top = '50%'; //Позиция по вертикали

        //Выравнивание по центру путем задания отрицательных отступов
        _winReg.style.marginTop = -(300 / 2) + 'px';
        _winReg.style.marginLeft = -(400 / 2) + 'px';
    },

    initWinForgotPass: function () {
        _winForgotPass = document.getElementById('modalwindow'); //Получаем наше диалоговое окно по ID
        //Если оно не определено, то также создадим его по аналогии
        if (!_winForgotPass) {
            var parent = document.getElementsByTagName('body')[0];
            var obj = parent.firstChild;
            _winForgotPass = document.createElement('div');
            _winForgotPass.id = 'modalwindow';
            _winForgotPass.style.padding = '0 0 5px 0';
            parent.insertBefore(_winForgotPass, obj);
        }
        _winForgotPass.style.width = "400px"; //Установим ширину окна
        _winForgotPass.style.height = "250px"; //Установим ширину окна
        _winForgotPass.style.display = 'inline'; //Зададим CSS-свойство

        _winForgotPass.innerHTML = '<div id="modal-window--wrapper"><form class="feedback-form"><label for="name">Ваше имя:</label><input type="text" class="user-name" name="name"><br><label for="email">Ваш e-mail:</label><input type="email" class="user-email" name="email"><br><div class="modal-button--wrapper"><input type="button" class="modal-cancel" onclick="modalWindow.close()" value="Отмена"><input type="submit" class="modal-submit" value="Отправить"></div></form></div>'
        //Установим позицию по центру экрана

        _winForgotPass.style.left = '50%'; //Позиция по горизонтали
        _winForgotPass.style.top = '50%'; //Позиция по вертикали

        //Выравнивание по центру путем задания отрицательных отступов
        _winForgotPass.style.marginTop = -(250 / 2) + 'px';
        _winForgotPass.style.marginLeft = -(400 / 2) + 'px';
    },

    initWinFeedBack: function () {
        _winFeedBack = document.getElementById('modalwindow'); //Получаем наше диалоговое окно по ID
        //Если оно не определено, то также создадим его по аналогии
        if (!_winFeedBack) {
            var parent = document.getElementsByTagName('body')[0];
            var obj = parent.firstChild;
            _winFeedBack = document.createElement('div');
            _winFeedBack.id = 'modalwindow';
            _winFeedBack.style.padding = '0 0 5px 0';
            parent.insertBefore(_winFeedBack, obj);
        }
        _winFeedBack.style.width = "400px"; //Установим ширину окна
        _winFeedBack.style.height = "400px"; //Установим ширину окна
        _winFeedBack.style.display = 'inline'; //Зададим CSS-свойство

        _winFeedBack.innerHTML = '<div id="modal-window--wrapper"><form class="feedback-form"><label for="name">Ваше имя:</label><input type="text" class="user-name" name="name"><br><label for="email">Ваш e-mail:</label><input type="email" class="user-email" name="email"><br><br><label for="textarea"> Сообщение: <textarea class="wind-feedback--style" rows="7" cols="40" name="text" maxlength="300"></textarea></label><div class="modal-button--wrapper"><input type="button" class="modal-cancel" onclick="modalWindow.close()" value="Отмена"><input type="submit" class="modal-submit" value="Отправить"></div></form></div>'
        //Установим позицию по центру экрана

        _winFeedBack.style.left = '50%'; //Позиция по горизонтали
        _winFeedBack.style.top = '50%'; //Позиция по вертикали

        //Выравнивание по центру путем задания отрицательных отступов
        _winFeedBack.style.marginTop = -(400 / 2) + 'px';
        _winFeedBack.style.marginLeft = -(400 / 2) + 'px';
    },


    close: function () {
        document.getElementById('blockscreen').style.display = 'none';
        document.getElementById('modalwindow').style.display = 'none';
    },

    showWinForgotPass: function () {
        modalWindow.initBlock();
        modalWindow.initWinForgotPass();
    },


    showWinFeedBack: function () {
        modalWindow.initBlock();
        modalWindow.initWinFeedBack();
    },

    showWinReg: function () {
        modalWindow.initBlock();
        modalWindow.initWinReg();
    }
}

//  функция на отарвку формы с переводом значений полей в JSON
  
var ops = function() {
    function JSONString(form) {
      var obj = {};
      var elements = form.querySelectorAll("input");
      for (var i = 0; i < elements.length; ++i) {
        var element = elements[i];
        var name = element.name;
        var value = element.value;

        if (name) {
          obj[name] = value;
        }
      }

      return JSON.stringify(obj);
    }

    document.addEventListener("DOMContentLoaded", function() {
      var form = document.getElementById("test");
//      var output = document.getElementById("output");
      form.addEventListener("submit", function(e) {
        e.preventDefault();
        var json = JSONString(this);
//        output.innerHTML = json;

      }, false);

    });

  };
ops();

//   здесь этот френки кончается