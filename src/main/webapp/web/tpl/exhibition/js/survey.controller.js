
angular.module('app.exhibition.survey.controllers', [])
    .controller('ExhibitionSurveyCtrl', ['$scope','$log','$stateParams','$state','$http',ctrlFn]);
;


function ctrlFn($scope,$log,$stateParams,$state,$http){
    $scope.surveyFieldList1 = [];
    $scope.surveyFieldList2 = [];
    $scope.surveyFieldList3 = [];
    $scope.tempList = [];

    $scope.id = 0;
    $scope.init = function(){
        $scope.id = $stateParams.id;
    };

    $scope.initData = function(){
        $scope.id = $stateParams.id;
        $log.info("id:"+$scope.id);

        $http.get($scope.app.projectName + '/mobile/exhibition/survey/field/1').success(function(result){
            //$log.info(result);

            $scope.surveyFieldList1 = result[0];
            $scope.surveyFieldList2 = result[1];
            if(result.length>=3) {
                $scope.surveyFieldList3 = result[2];
            }

            angular.forEach($scope.surveyFieldList1, function(field){
                if(field.fieldName=='brand'){
                    angular.forEach(field.optionList, function(option){
                        angular.forEach(option.childDataList, function(child){
                            $scope.tempList.push({"id":child.id,"name":child.name});
                        });
                    });
                }
            });
            $scope.tempList.push({"id":0,"name":""});
            //$log.info($scope.tempList);


            if($scope.id>0){
                $http.get($scope.app.projectName + '/mobile/exhibition/survey/'+$scope.id).success(function(result){
                    $scope.mergedata($scope.surveyFieldList1,result);
                    $scope.mergedata($scope.surveyFieldList2,result);
                    $scope.mergedata3($scope.surveyFieldList3,result['publicShow']);

                }).error(function(){
                    alert('问卷数据获取失败');
                });
            }

        }).error(function(){
            alert('问卷模版获取失败');
        });


    };

    $scope.mergedata = function (list,data){
        console.log("length1="+list.length);
        angular.forEach(list, function(field){
            if(field.indicator!='publicResource') {
                if (field.fieldFormat == 'checkbox') {
                    data[field.fieldName].forEach(function (item) {
                        field.fieldArr[item] = true;
                    });
                } else {
                    field.fieldVal = data[field.fieldName];
                }
            }
        });
    };
    $scope.mergedata3 = function (list,data){
        console.log("data3="+data);
        if(data!=null) {
            angular.forEach(list, function (field) {
                if (field.indicator == 'publicResource') {
                    if (field.fieldFormat == 'checkbox') {
                        data[field.fieldName].forEach(function (item) {
                            field.fieldArr[item] = true;
                        });
                    } else {
                        field.fieldVal = data[field.fieldName];
                    }
                }
            });
        }
    };


    $scope.maxNo = [0,0];

    //$scope.disabledSelect = 'N';
    //$scope.showSelect = function(code){
    //    $scope.disabledSelect = code;
    //};

    $scope.firstChange=true;
    $scope.grandchildDataList = [];

    $scope.childChange = function(idx,childId) {
        //$log.info(idx+"---"+childId);
        var optionList = $scope.surveyFieldList1[idx].optionList;   //[圣象,慕斯]

        if(!$scope.firstChange){
            for(var i=0;i<$scope.tempList.length;i++){
                if(childId==$scope.tempList[i].id){
                    childId = $scope.tempList[i-1].id;
                }
            }
        }
        //$log.info("--childId2="+childId);
        $scope.firstChange=false;
        $scope.grandchildDataList = [];
        for(var i=0;i<optionList.length;i++){
            for(var j=0;j<optionList[i].childDataList.length;j++) {
                if (optionList[i].childDataList[j].id == childId) {
                    $scope.grandchildDataList =optionList[i].childDataList[j].grandchildDataList;
                    return;
                }
            }
        }
        //$log.info("--grandchildDataList="+$scope.grandchildDataList);
    };


    $scope.brand2='';
    $scope.brand3='';
    $scope.fieldVal={"exhibitionId":1};
    $scope.fieldVal.publicShow={};
    $scope.surveySubmit = function(){
        $log.info("brand2="+$scope.brand2);
        $log.info("brand3="+$scope.brand3);

        submitDate($scope.surveyFieldList1);
        submitDate($scope.surveyFieldList2);
        submitDate3($scope.surveyFieldList3);
        $scope.fieldVal['id']=$scope.id;

        $scope.fieldVal['brand2'] = $scope.brand2;
        $scope.fieldVal['brand3'] = $scope.brand3;

        //$log.info($scope.fieldVal);

        $http.post($scope.app.projectName + '/mobile/exhibition/survey/save',$scope.fieldVal).success(function(result){
            if(result.code=='10000'){
                alert("保存成功");
                $state.go('app.exhibition.surveyList');
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
        });
    }
    function submitDate3(list){
        var publicShow={};
        angular.forEach(list, function(field){
            if(field.fieldFormat=='checkbox'){
                publicShow[field.fieldName]=[];
                angular.forEach(field.optionList, function(option){
                    if(field.fieldArr[option.id]){
                        //$log.info(option.id+"---"+field.fieldArr[option.id]);
                        publicShow[field.fieldName].push(option.id);
                    }
                });
            }else if(field.fieldVal!=null){
                publicShow[field.fieldName] = field.fieldVal;
            }
        });
        publicShow['surveyId']=$scope.id;
        $scope.fieldVal.publicShow=publicShow;
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

}
