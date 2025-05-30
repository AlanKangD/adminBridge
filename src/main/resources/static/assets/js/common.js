$(document).ready(function () {
   if ($(".line_tab") || $(".vertical_tab")) { LineTabMenuInit() }
   if ($(".btn_toggle").find("input[disabled='true']")) { toggleBtnDisabled() }
   if($('.file_uploader')){ fileUploader() }
   if($('.input_writing_group textarea')){ initCountString() }
   if ($('.progress_bar')) { progressBarUI() }
   if ($('.pagination').length > 0) { paginationUI() }

 
      
   /****** Tab Menu ******/
   $('.tab_menu .tab_list').click(function () { tabMenu(this) });

   function tabMenu(el) {
      var tab = $(el).parents('.tab_menu');
      var activeTab = $(el).attr('data-tab');
      $(el).siblings('li').removeClass('current');
      $(el).addClass('current');
      tab.next('.tab_cont').find('.tab_cont_item').stop().hide();
      tab.next('.tab_cont').find('#' + activeTab).stop().show();

      if (tab.hasClass("line_tab") || tab.hasClass("vertical_tab")) {
         //클릭시 라인이동
         var tab = $(el).parents('.tab_menu');
         var tabBar = $(el).parents('.tab_menu').find('.tab_bar');
         if (tab.hasClass("line_tab")) {
            var liWidth = tab.find(".current").outerWidth();
            var marginLeft = parseInt(tab.find(".current").css("margin-left"));
            var left = tab.find(".current").position().left + marginLeft;
            tabBar.css({
               "width": liWidth,
               "left": left
            });
         } else if (tab.hasClass("vertical_tab")) {
            var liH = tab.find(".current").outerHeight();
            var marginTop = parseInt(tab.find(".current").css("margin-top"));
            var top = tab.find(".current").position().top + marginTop;
            tabBar.css({
               "height": liH,
               "top": top
            });
         }
      }
   }

   //Line Tab 초기화
   function LineTabMenuInit() {
      var tabM = $('.tab_menu');
      var lineTab = $('.line_tab');
      var verticalLineTab = $(".vertical_tab");
      if (tabM.hasClass("line_tab") || tabM.hasClass("vertical_tab")) {
         tabM.each(function () {
            if ($(this).find('.tab_bar').length < 1) {
               tabM.append("<div class='tab_bar'></div>");
            };
         });
      }
      lineTab.each(function () {
         console.log($(this))
         $(this).find('.tab_bar').css({
            "width": $(this).find(".current").outerWidth(),
            "left": $(this).find(".current").position().left + parseInt($(this).find(".current").css("margin-left"))
         });
      })
      verticalLineTab.each(function () {
         $(this).find(".tab_bar").css({
            "height": $(this).find(".current").outerHeight(),
            "top": $(this).find(".current").position().top + parseInt($(this).find(".current").css("margin-top"))
         });
      })
   };

   /****** Toggle Button ******/
   $('.btn_toggle').click(function () {
      if($(this).next('.btn_toggle_txt')){toggelBtnText(this)}
   })
   //Toggle Button Change Text
   function toggelBtnText(el){
      var toggleON = $(el).find('input[type=checkbox]').is(":checked");
      var toggleText = $(el).next('.btn_toggle_txt');
      var toggleTextValue = $(el).next('.btn_toggle_txt').text();
      if (toggleON) {
         if (toggleTextValue == 'OFF') {
            toggleText.text('ON');
         } else if (toggleTextValue == 'Unchecked toggle') {
            toggleText.text('Checked toggle');
         }
      } else {
         if (toggleTextValue == 'ON') {
            toggleText.text('OFF');
         } else if (toggleTextValue == 'Checked toggle') {
            toggleText.text('Unchecked toggle');
         }
      }
   }
   //Toggle Button Disabled
   function toggleBtnDisabled() {
      $('.btn_toggle').each(function (index, item) {
         var toggleDis = $(item).find('input[type=checkbox]').is(":disabled");
         if (toggleDis) {
            $(item).addClass('disabled');
         } else {
            $(item).removeClass('disabled');
         }
      });
   }

   /****** File Uploader ******/
   function fileUploader(){
      $('.file_uploader').each(function (index, item) {
         $(item).find('.file_name .input_delete').on('click', function () {
            $(this).parents('.file_name').remove();
         });
         $(item).find('.input_file').on('change', function () {
            var fileCheck = $(this).val();
            if (fileCheck == '') {
               alert("파일을 첨부해 주세요");
            } else {
               var $div = $('<div class="file_name"><input type="text" readonly><i class="input_delete" onclick="removeFilename(this)"></i></div>');
               $(item).append($div);
               var fileName = $(this).val();
               //경로가 있는경우
               //$div.find('input').val(fileName);
               //경로가 없어야 하는 경우
               fileName = fileName.split("\\");
               $div.find('input').val(fileName[fileName.length - 1]);
            }
         });
      });
   }


   /****** Select Box ******/
   $(document).on('click', '.select_box_value', function (e) {
      const t = $(this);
      if ($(this).parents('.select_box').hasClass('on')) {
         dropDownClose(t);
      } else {
         if ($(this).parents('.select_box').hasClass('disabled')) {
            return false;
         }
         $('.select_box').removeClass('on');
         selectBoxDown(t);
      }
   });
   $(document).on('click', '.select_box_list li', function (e) {
      selectBoxDownAction(this);
      SelectBoxChange(this);
   });

   function selectBoxDown(t) {
      const $selectBox = t.parents('.select_box');
      if (!t.hasClass('disabled')) {
         if ($selectBox.hasClass('on')) {
            $selectBox.removeClass('on')
         } else {
            $selectBox.addClass('on');
            $selectBox.siblings('.select_box').removeClass('on');
         }
         $('body').on('click', function (e) {
            if ($(e.target).closest('.select_box').length === 0 && $('.select_box').hasClass('on')) {
               dropDownClose()
            }
         });
      };
   };

   function selectBoxDownAction(el) {
      $(el).parents('.select_box_list').find('li').removeClass('selected');

      if (!$(el).parent('li').hasClass('disabled')) {
         $(el).addClass('selected');
      }
      $(el).parents('.select_box').removeClass('on')
   };

   function dropDownClose() {
      $('.select_box').removeClass('on');
   };
   //Change Select Box Value
   function SelectBoxChange(selectItem) {
      if ($(selectItem).find('ul').length <= 0) {
         var $cloneEle = $(selectItem).parents('.select_box').find('.select_box_value').children('span').children();
         var selectText = $(selectItem).text();
         clearInput(selectItem);
         $(selectItem).parents('.select_box').find('.select_box_value').children('span').text(selectText);
         $(selectItem).parents('.select_box').find('.select_box_value').children('span').append($cloneEle);
      }
   };

   function clearInput(obj) {
      $(obj).parents('.select_box').find('.select_box_value').children('span').text("");
   };


   /****** Accordion ******/
   $(".accord_head").click(function (){accordionUI(this)});
 
   function accordionUI(el){
      if ($(el).hasClass("on")) {
         $(el).removeClass("on");
         $(el).find(".accord_cont").stop().slideUp();
      } else {
         $(el).parent('.accord_list').find('.accord_head').removeClass("on");
         $(el).parent('.accord_list').find(".accord_cont").stop().slideUp();
         $(el).addClass("on");
         $(el).find(".accord_cont").stop().slideDown();
      }
   }


   /****** Data Tables ******/
   $('.dataTables_wrapper .dataTables_length').click(function () {
      $(this).toggleClass('on');
   });
   $('body').on('click', function (e) {
      if ($(e.target).closest('.dataTables_length').length === 0 && $('.dataTables_length').hasClass('on')) {
         $('.dataTables_length').toggleClass('on');
      }
   });

   /****** TextArea String Length Count ******/
   $(".input_writing_group").find('textarea').on('keyup', function () {CountString(this)});
   //textarea 기존 값이 있는 경우 Count String
   function initCountString(){
      $('.input_writing_group').each(function (index, item) {  
         var st = $(item).find('textarea').val();
         $(item).find('.txt_count').find('.current').html(st.length)
      });
   }
   //Count String
   function CountString(el){
      var regex = /[^0-9]/g; //숫자추출 정규식
      var total = $(el).next('.txt_count').find('.total').html().replace(regex, "");
      $(el).next('.txt_count').find('.current').html($(el).val().length);
      if ($(el).val().length > total) {
         alert(total + '자 이내로 작성해주세요')
         $(el).val($(el).val().substring(0, total));
         $(el).next('.txt_count').find('.current').html(total);
      };
   }

   /****** Progress bar ******/
   function progressBarUI(){
      $(".progress_bar").each(function (i, block) {
         var regex = /[^0-9]/g; //숫자추출 정규식
         var progressR = $(block).html().replace(regex, "");//끝 값   
         var width = 0; //시작값
         var id = setInterval(frame, 15);//너비, 숫자표시 증가속도
         function frame() {
            if (progressR >= 100) {
               progressR = 100;
               $(block).css('width', 100 + '%'); //너비       
            }
            if (width >= progressR) {
               clearInterval(id);
               cnt = 0;
            } else {
               width++;
               $(block).css('width', width + '%'); //너비
               $(block).find('.progress_ratio').html(width + '%');  //숫자 표시
            }
         }
      });
   }

   /****** Pagination ******/

   function paginationUI(){
      $('.pagination').each(function (index, item) {
         $(item).find('a').on('click', function () {
            if(!$(this).hasClass('arr')){
               $(this).siblings().removeClass('active')
               $(this).addClass('active');
            }
         });
      });
   }

}) //ready


//File Uploader - Remove Choosed File
function removeFilename(t) {
   $(t).parents('.file_name').remove();
};

//DataTable Select All row
function dataTableSelect(dtable) {
   var dtable = dtable;
   $(".dataTable  .checkall").prop("checked", false);
   $(".checkall").click(function () {
      if ($(this).prop("checked")) {
         dtable.rows().select();
      } else {
         dtable.rows().deselect();
      }
   });
};







/****** Framework Copy Snippet ******/
function snippetCopy() {
   //Snippet show/hide
   $('.snippet_btn').click(function () {
      $(this).parents('.snippet_btn_wrap').next('.snippet').stop().slideToggle();
   });
   //Prism textarea convert
   $("textarea[name='code']").each(function (i, block) {
      var className = $(block).attr('class');
      var sourceCode = $(block).html();
      var prefix = "<pre";
      var commandOption = $(block).attr('command-line');
      if (commandOption !== undefined) {
         var user = commandOption.split(" ")[0] || "user";
         var host = commandOption.split(" ")[1] || "localhost";
         prefix += " class='command-line' data-user='" + user + "' data-host='" + host + "'>";
      } else {
         prefix += " class='line-numbers'>";
      };
      prefix += "<code class='" + className + "' id='textDiv'>";
      var postfix = "</code></pre>";
      $(block).after(prefix + sourceCode + postfix);
      $(block).remove();
   });
   //Copy Button
   $(".copy_btn").on("click", function () {
      var textDiv = $(this).prev(".language-markup").find('#textDiv');
      var txt = textDiv.text();
      var createInput = $('<textarea></textarea>');
      var copyAlert = $(this).next('.copy_alert');
      textDiv.append(createInput);
      createInput.val(txt);
      createInput.select();
      document.execCommand('copy');
      createInput.remove();
      copyAlert.addClass('show');
      setTimeout(function () {
         copyAlert.removeClass('show');
      }, 500);
   });
}