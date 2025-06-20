var fileCnt = 1;
$(function(){

    $('#etc_reg').on("click", function(){
        let lstWrapTxt = '<li class="material_inner">';
        lstWrapTxt += '<input type="hidden" name="recipeEtcIngredient" value="startPoint">';
        lstWrapTxt += '<input type="hidden" name="recipeEtcQuantity" value="startPoint">';
        lstWrapTxt += '<div class="input_group input_area material_tit">';
        lstWrapTxt += '<input type="text" name="recipeEtc" class="input_text" value="" placeholder="재료 묶음 이름">';
        lstWrapTxt += '<button type="button" class="btn btn_sm btn_secondary mat_innr_minus" onclick="deleteListWrap(this)">';
        lstWrapTxt += '<i class="ico_del white ico_24"></i>';
        lstWrapTxt += '재료/양념 묶음 삭제';
        lstWrapTxt += '</button>';
        lstWrapTxt += '</div>';
        lstWrapTxt += '<div class="material_lst_wrap">';
        lstWrapTxt += '<ul id="material_lst" class="material_lst">';
        lstWrapTxt += '<li>';
        lstWrapTxt += '<input type="text" name="recipeEtcIngredient" class="input_text" placeholder="예) 돼지고기">';
        lstWrapTxt += '<input type="text" name="recipeEtcQuantity" class="input_text" placeholder="예) 300g">';
        lstWrapTxt += '<button type="button" class="btn btn_md btn_secondary btn_radius btn_icon material_lst_del" onclick="deleteList(this)">';
        lstWrapTxt += '<i class="ico_del white ico_24"></i>';
        lstWrapTxt += '</button>';
        lstWrapTxt += '</li>';
        lstWrapTxt += '</ul>';
        lstWrapTxt += '<div class="lst_plus_wrap">';
        lstWrapTxt += '<button type="button" id="addItemButton" class="btn btn_md btn_border btn_icon mat_lst_plus" onclick="addList(this)">';
        lstWrapTxt += '<i class="ico_add black ico_24"></i>';
        lstWrapTxt += '</button>';
        lstWrapTxt += '</div>';
        lstWrapTxt += '</div>';
        lstWrapTxt += '</li>';

       // const myList = document.getElementById('material_wrap');

        //myList.append(lstWrapTxt);
        $('.material_wrap').append(lstWrapTxt);
    });

    $('#etc_mod').on("click", function(){
        let lstWrapTxt = '<ul class="material_wrap">';
        lstWrapTxt += '<li class="material_inner">';
        lstWrapTxt += '<input type="hidden" name="recipeEtcIngredient" value="startPoint">';
        lstWrapTxt += '<input type="hidden" name="recipeEtcQuantity" value="startPoint">';
        lstWrapTxt += '<div class="input_group input_area material_tit">';
        lstWrapTxt += '<input type="text" name="recipeEtc" class="input_text" value="" placeholder="재료 묶음 이름">';
        lstWrapTxt += '<button type="button" class="btn btn_sm btn_secondary mat_innr_minus" onclick="deleteListWrap(this)">';
        lstWrapTxt += '<i class="ico_del white ico_24"></i>';
        lstWrapTxt += '재료/양념 묶음 삭제';
        lstWrapTxt += '</button>';
        lstWrapTxt += '</div>';
        lstWrapTxt += '<div class="material_lst_wrap">';
        lstWrapTxt += '<ul id="material_lst" class="material_lst">';
        lstWrapTxt += '<li>';
        lstWrapTxt += '<input type="text" name="recipeEtcIngredient" class="input_text" placeholder="예) 돼지고기">';
        lstWrapTxt += '<input type="text" name="recipeEtcQuantity" class="input_text" placeholder="예) 300g">';
        lstWrapTxt += '<button type="button" class="btn btn_md btn_secondary btn_radius btn_icon material_lst_del" onclick="deleteList(this)">';
        lstWrapTxt += '<i class="ico_del white ico_24"></i>';
        lstWrapTxt += '</button>';
        lstWrapTxt += '</li>';
        lstWrapTxt += '<li>';
        lstWrapTxt += '<input type="text" name="recipeEtcIngredient" class="input_text" placeholder="예) 양배추">';
        lstWrapTxt += '<input type="text" name="recipeEtcQuantity" class="input_text" placeholder="예) 1/2개">';
        lstWrapTxt += '<button type="button" class="btn btn_md btn_secondary btn_radius btn_icon material_lst_del" onclick="deleteList(this)">';
        lstWrapTxt += '<i class="ico_del white ico_24"></i>';
        lstWrapTxt += '</button>';
        lstWrapTxt += '</li>';
        lstWrapTxt += '<li>';
        lstWrapTxt += '<input type="text" name="recipeEtcIngredient" class="input_text" placeholder="예) 소금">';
        lstWrapTxt += '<input type="text" name="recipeEtcQuantity" class="input_text" placeholder="예) 1T">';
        lstWrapTxt += '<button type="button" class="btn btn_md btn_secondary btn_radius btn_icon material_lst_del" onclick="deleteList(this)">';
        lstWrapTxt += '<i class="ico_del white ico_24"></i>';
        lstWrapTxt += '</button>';
        lstWrapTxt += '</li>';
        lstWrapTxt += '</ul>';
        lstWrapTxt += '<div class="lst_plus_wrap">';
        lstWrapTxt += '<button type="button" id="addItemButton" class="btn btn_md btn_border btn_icon mat_lst_plus" onclick="addList(this)">';
        lstWrapTxt += '<i class="ico_add black ico_24"></i>';
        lstWrapTxt += '</button>';
        lstWrapTxt += '</div>';
        lstWrapTxt += '</div>';
        lstWrapTxt += '</li>';
        lstWrapTxt += '</ui>';

        $('#etcList').append(lstWrapTxt);

    });
});

// 확장자가 이미지 파일인지 확인
function isImageFile(file) {
    var ext = file.name.split(".").pop().toLowerCase(); // 파일명에서 확장자를 가져온다.
    return ($.inArray(ext, ["jpg", "jpeg", "gif", "png"]) === -1) ? false : true;
}

// 파일의 최대 사이즈 확인
function isOverSize(file) {
    var maxSize = 3 * 1024 * 1024; // 3MB로 제한
    return (file.size > maxSize) ? true : false;
}

//재료 추가 삭제
function deleteList(obj) {

    obj.closest('li').remove();

    let targetId = 'fileList-';
    let li = $('.step_lst li').length;

    for( var i = 0 ; i <= li ; i++ ) {
        $('.step_lst li').eq(i).attr('id', targetId + i);
        $('#'+ targetId + i + ' .stepTitNum').text(i+1);
    }

}

//재료 묶음 삭제
function deleteListWrap(e) {
    e.closest('li').remove();
}

//재료 추가
function addList(test) {
    let text = "<li>";
    text += "<input type='text' name='recipeEtcIngredient' class='input_text' placeholder='예) 재료명'>";
    text += "<input type='text' name='recipeEtcQuantity' class='input_text' placeholder='예) 용량'>";
    text += "<button type='button' class='btn btn_md btn_secondary btn_radius btn_icon material_lst_del' onclick='deleteList(this)'>";
    text += "<i class='ico_del white ico_24'></i>";
    text += "</button>";
    text += "</li>";

    let mat_lst = $(test).closest(".lst_plus_wrap").closest(".material_lst_wrap").find(".material_lst");
    mat_lst.append(text);
}

// step length
function stepLength(test1){
    let stepSize = $(".step_lst li").length;
    console.log(stepSize);
}

//스텝 추가
function addStep(obj){

    let text = "<li>";
    text += "<p>Step<span class='stepTitNum'>1</span></p>";
    text += "<div class='input_writing_group input_area step_txt'>";
    text += "<textarea name='recipeContent' placeholder='내용을 입력해주세요.'></textarea>";
    text += "</div>";
    text += "<div class='step_pic_wrap'>";
    text += "<input type='hidden' name='stepFileName' class='step_upload' accept='image/* multiple'>";
    text += "<img class='step_pic_add' onclick='clickImage(this)' src='/assets/images/add_pic.gif' alt='step 이미지 추가'>";
    text += "</div>";
    text += "<div class='del_btn_wrap'>";
    text += "<button type='button' class='btn btn_md btn_secondary btn_radius btn_icon material_lst_del' onclick='deleteList(this);'><i class='ico_del white ico_24'></i></button>";
    text += "</div>";
    text += "</li>";

    let step_lst = $(obj).closest(".step_plus_wrap").siblings(".step_lst");
    step_lst.append(text);
    let i = $('.step_lst li').length;
    let targetId = 'stepList';

    for(let j = 0 ; j <= i ; j++){
        $('.step_lst li').eq(j).attr('id', 'stepList' + j);
        $('#'+targetId+j+' .stepTitNum').text(j+1);
        //이미지 추가
    }
}
let click = null;
let clickLocation = null;

function clickImage(obj) {

    // 기존 input 제거 (중복 방지)
    $(obj).siblings('input[type="file"]').remove();
    var hiddenValue = $(obj).parent().find('input[type="hidden"]').first().val();

    // 새로운 input file 생성
    var $fileInput = $('<input/>', {
        type: 'file',
        name: 'file',
        style: 'display:none'
    });

    $(obj).parent().append($fileInput);
    $fileInput.click();

    // change 이벤트 감지하여 Ajax 전송
    $fileInput.on('change', function(event) {
        var file = event.target.files[0];
        if (!file) return;

        var reader = new FileReader();
        reader.onload = function(e) {
            $(obj).attr("src", e.target.result); // 미리보기
        };
        reader.readAsDataURL(file);

        var formData = new FormData();
        formData.append('file', file);

        $.ajax({
            url: '/upload/image',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                console.log('업로드 완료:', response.body.fileName);
               // $('#recipeFileName').val(response.body.fileName);
                // 업로드된 이미지 URL을 hidden input에 저장하거나 서버 저장 경로 관리
                $(obj).parent().find('input[type="hidden"]').first().val(response.body.fileName);
                // 필요 시 $(obj).data('uploaded-url', response.url);
            },
            error: function(xhr) {
                console.error('업로드 실패', xhr);
            }
        });
    });
}






