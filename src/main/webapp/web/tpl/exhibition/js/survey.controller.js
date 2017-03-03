
angular.module('app.exhibition.survey.controllers', [])
    .controller('ExhibitionSurveyCtrl', ['$scope','$log','$stateParams','$state','$http',ctrlFn]);
;


function ctrlFn($scope,$log,$stateParams,$state,$http){
    $scope.surveyFieldList1 = [];
    $scope.surveyFieldList2 = [];
    $scope.surveyFieldList3 = [];

    $scope.id = 0;
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

        $http.get($scope.app.projectName + '/mobile/exhibition/survey/field/1').success(function(result){
            //$log.info(result);

            $scope.surveyFieldList1 = result[0];
            $scope.surveyFieldList2 = result[1];
            if(result.length>=3) {
                $scope.surveyFieldList3 = result[2];
            }

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
                $http.get($scope.app.projectName + '/mobile/exhibition/survey/'+$scope.id).success(function(result){
                    mergedata($scope.surveyFieldList1,result);
                    mergedata($scope.surveyFieldList2,result);
                    mergedata3($scope.surveyFieldList3,result.publicShow,result.otherOptionVo);

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
                if (field.indicator != 'publicResource') {
                    if (field.fieldFormat == 'checkbox') {
                        if(data[field.fieldName]!=null) {
                            data[field.fieldName].forEach(function (item) {
                                field.fieldArr[item] = true;
                            });
                        }
                    } else {
                        field.fieldVal = data[field.fieldName];
                    }
                    if(data.otherOptionVo!=null) {
                        field.otherOptionVo[field.fieldName] = data.otherOptionVo[field.fieldName];
                    }
                }
            });
            $scope.fieldVal.exhibitionId=data.exhibitionId;
        }
    };
    function mergedata3(list,pbshow,otherOptionVo){
        if(pbshow!=null) {
            angular.forEach(list, function (field) {
                //$log.info(field.otherOptionVo);
                if (field.indicator == 'publicResource') {
                    if (field.fieldFormat == 'checkbox') {
                        if(pbshow[field.fieldName]!=null) {
                            pbshow[field.fieldName].forEach(function (item) {
                                field.fieldArr[item] = true;
                            });
                        }
                    } else {
                        field.fieldVal = pbshow[field.fieldName];
                    }
                    if(otherOptionVo!=null) {
                        field.otherOptionVo[field.fieldName] = otherOptionVo[field.fieldName];
                    }
                }
            });
        }
    };


    $scope.selectEhb = function(marketId){
        //$log.info("marketId:"+marketId);
        $scope.fieldVal.exhibitionId='';

        if($scope.marketList!=null) {
            $scope.marketList.forEach(function (m) {
                if (marketId == m.id) {
                    //$log.info("m.cityId:" + m.cityId);
                    angular.forEach($scope.surveyFieldList1, function (field) {
                        if (field.fieldName == 'exhibitionCity') {
                            field.fieldVal = m.cityId;
                        }
                    });
                }
            })
        }

    };
    $scope.ehbComparator = function(expected, actual){
        //$log.info(expected+"----"+actual+"----"+(expected==actual));
        return expected==actual;
    };






    $scope.fieldVal={};
    $scope.fieldVal.publicShow={};
    $scope.fieldVal.otherOptionVo={};
    $scope.surveySubmit = function(){

        if($scope.fieldVal.exhibitionId==''){
            alert("请先选择展厅");
        }
        submitDate($scope.surveyFieldList1);
        submitDate($scope.surveyFieldList2);
        submitDate3($scope.surveyFieldList3);
        $scope.fieldVal['id']=$scope.id;


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
            if(field.otherOptionVo[field.fieldName]!=undefined && field.otherOptionVo[field.fieldName]!=null) {
                $scope.fieldVal.otherOptionVo[field.fieldName] = field.otherOptionVo[field.fieldName];
                //$log.info("otherOptionVo1["+field.fieldName+"]=" + $scope.fieldVal.otherOptionVo[field.fieldName]);
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
            if(field.otherOptionVo[field.fieldName]!=undefined && field.otherOptionVo[field.fieldName]!=null) {
                $scope.fieldVal.otherOptionVo[field.fieldName] = field.otherOptionVo[field.fieldName];
                //$log.info("otherOptionVo3["+field.fieldName+"]=" + $scope.fieldVal.otherOptionVo[field.fieldName]);
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
