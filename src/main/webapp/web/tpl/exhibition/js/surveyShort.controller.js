
angular.module('app.exhibition.surveyShort.controllers', [])
    .controller('ExhibitionSurveyShortCtrl', ['$scope','$log','$stateParams','$state','$http','$anchorScroll','$modal','$window',ctrlFn]);
;


function ctrlFn($scope,$log,$stateParams,$state,$http,$anchorScroll,$modal,$window){
    $scope.surveyFieldList1 = [];

    $scope.id = 0;
    $scope.formId = 1;
    $scope.init = function(){
        $scope.id = $stateParams.id;
    };

    $scope.initData = function(){
        $scope.id = $stateParams.id;
        $log.info("id:"+$scope.id);

        $scope.marketList = [];
        $scope.exhibitionList = [];
        $http.get($scope.app.projectName + '/mobile/exhibition/store/list').success(function(result){
            $scope.marketList=result.marketList;
            $scope.exhibitionList=result.exhibitionList;
        });

        $http.get($scope.app.projectName + '/mobile/exhibition/survey/field/short/'+$scope.formId).success(function(result){
            //$log.info(result);

            $scope.surveyFieldList1 = result;

            var newOptionList = [];
            for(var i=0;i<$scope.surveyFieldList1.length;i++) {
                var field = $scope.surveyFieldList1[i];
                if (field.fieldName == 'brand') {
                    angular.forEach(field.optionList, function (option) {
                        newOptionList.push({"id":option.id,"name":option.name,"type":option.type});
                        angular.forEach(option.childDataList, function (child) {
                            newOptionList.push({"id":child.id,"name":"• "+child.name,"type":option.type});
                            //angular.forEach(child.grandchildDataList, function (grand) {
                            //    newOption.childDataList.push({"id":grand.id,"name":"•• "+grand.name});
                            //});
                        });
                        //newOptionList.push(newOption);
                    });
                    field.optionList = newOptionList;
                    break;
                }
            }

            if($scope.id>0){
                $http.get($scope.app.projectName + '/mobile/exhibition/survey/short/'+$scope.id).success(function(result){
                    mergedata($scope.surveyFieldList1,result);

                }).error(function(){
                    alert('问卷数据获取失败');
                });
            }

        }).error(function(){
            alert('问卷模版获取失败');
        });


    };

    function mergedata(list,data){
        if(data!=null) {
            angular.forEach(list, function (field) {
                //if (field.indicator != 'publicResource') {
                    if (field.fieldFormat == 'checkbox') {
                        data[field.fieldName].forEach(function (item) {
                            field.fieldArr[item] = true;
                        });
                    } else {
                        field.fieldVal = data[field.fieldName];
                    }
                    if(data.otherOptionVo!=null) {
                        field.otherOptionVo[field.fieldName] = data.otherOptionVo[field.fieldName];
                    }
                    if(data.photoListVo!=null) {
                        field.photoListVo[field.fieldName] = data.photoListVo[field.fieldName];
                    }
                //}
            });
            $scope.fieldVal.marketId=data.marketId;
            $scope.fieldVal.exhibitionId=data.exhibitionId;
        }
    };


    $scope.selectEhb = function(marketId){
        //$log.info("marketId:"+marketId);
        $scope.fieldVal.exhibitionId='';

        $scope.marketList.forEach(function (m){
            if(marketId == m.id) {
                //$log.info("m.cityId:" + m.cityId);
                //$scope.fieldVal.exhibitionCity = m.cityId;
                angular.forEach($scope.surveyFieldList1, function (field) {
                    if (field.fieldName == 'exhibitionCity') {
                        field.fieldVal = m.cityId;
                    }
                });
            }
        })

    };
    $scope.ehbComparator = function(expected, actual){
        //$log.info(expected+"----"+actual+"----"+(expected==actual));
        return expected==actual;
    };




    $scope.fieldVal={"shortFlag":1};
    $scope.fieldVal.otherOptionVo={};
    $scope.surveySubmit = function(copyFlag){
        //$log.info("--copyFlag="+copyFlag);

        if($scope.fieldVal.exhibitionId==''){
            alert("请先选择展厅");
        }
        submitDate($scope.surveyFieldList1);
        $scope.fieldVal.id=$scope.id;
        $scope.fieldVal.formId=$scope.formId;


        //$log.info($scope.fieldVal);

        $http.post($scope.app.projectName + '/mobile/exhibition/survey/save',$scope.fieldVal).success(function(result){
            if(result.code=='10000'){
                if(!copyFlag) {
                    alert("保存成功");
                    $state.go('app.exhibition.surveyList');
                }else{
                    alert("保存成功，继续填写");
                    $scope.id = 0;
                    $scope.fieldVal.marketId = 0;
                    $scope.fieldVal.exhibitionId = 0;
                    $anchorScroll();
                }

            }else{
                alert(result.msg);
            }
        }).error(function () {
            alert('保存失败');
        });
    };

    function submitDate(list){
        angular.forEach(list, function(field){
            if(field.fieldFormat=='checkbox'){
                $scope.fieldVal[field.fieldName]=[];
                angular.forEach(field.optionList, function(option){
                    if(field.fieldArr[option.id]){
                        //$log.info(option.id+"---"+field.fieldArr[option.id]);
                        $scope.fieldVal[field.fieldName].push(option.id);
                    }
                });
            }else if(field.fieldVal!=null){
                $scope.fieldVal[field.fieldName] = field.fieldVal;
            }
            if(field.otherOptionVo[field.fieldName]!=undefined && field.otherOptionVo[field.fieldName]!=null) {
                $scope.fieldVal.otherOptionVo[field.fieldName] = field.otherOptionVo[field.fieldName];
                $log.info("otherOptionVo1["+field.fieldName+"]=" + $scope.fieldVal.otherOptionVo[field.fieldName]);
            }
        });
    }


    $scope.delete = function(){
        if($scope.id!='' && confirm("是否确认删除！")) {
            $http.get($scope.app.projectName+'/mobile/exhibition/survey/delete/'+$scope.id).success(function (result) {
                if(result.code=='10000'){
                    alert("删除成功");
                    $state.go('app.exhibition.surveyList');
                }else{
                    alert(result.msg);
                }
            }).error(function () {
                alert('删除失败');
            });
        }
    };

    $scope.photoList;
    $scope.projectName;
    $scope.fieldDesc;
    var ModalInstanceCtrl = function ($scope, $modalInstance, photoList,projectName,fieldDesc) {

        $scope.photoList = photoList;
        $scope.projectName = projectName;
        $scope.fieldDesc = fieldDesc;

        $scope.uploadOne = function () {
            $scope.photoList.push({"id":0,"realName":"test1","minUrl" : "/uploadFile/2016-09-01/photo/c13372a0-460f-435f-871f-6328ca38ad24-1347-min.gif","fileUrl" : "/uploadFile/2016-09-01/photo/c13372a0-460f-435f-871f-6328ca38ad24-1347.gif"});
        };

        $scope.photoOpen = function (url) {
            $window.open(url, '', 'width=800,height=600');
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.deletePhoto = function (photoId) {
            if(confirm("是否确认删除！")) {
                $log.info("photoId=" + photoId);
                angular.forEach($scope.photoList, function (photo, idx) {
                    if (photo.id == photoId) {
                        $scope.photoList.splice(idx, 1);
                    }
                });
            }
        };
    };

    $scope.photoUpload = function (size,photoList,fieldDesc) {
        var modalInstance = $modal.open({
            templateUrl: 'photoList',
            controller: ModalInstanceCtrl,
            size: size,
            resolve: {
                photoList: function () {
                    return photoList;
                },fieldDesc: function () {
                    return fieldDesc;
                },projectName: function () {
                    return $scope.app.projectName;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            $log.info(selectedItem);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
}
