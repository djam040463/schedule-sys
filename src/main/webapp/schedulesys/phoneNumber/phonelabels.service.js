(function() {
	'use strict';
	
	angular
		.module('scheduleSys')
		.factory('PhoneLabelService', PhoneLabelService);
	
	PhoneLabelService.$Inject = ['$resource'];
	
	function PhoneLabelService($resource) {
		console.log('calling phone label service');
		var resourceUrl = '/phone-number-labels/:id';
		
		return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'remove':  {
                method: 'DELETE',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.toJson(data);
                    }
                    return data;
                }
            },
            'save': {
                method: 'POST',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.toJson(data);
                    }
                    return data;
                }
            
            }
        });
		
	}
	
})();