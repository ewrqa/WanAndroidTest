/// data : "..."
/// errorCode : 0
/// errorMsg : ""

class ApiRsponse {
  ApiRsponse({
      String data, 
      int errorCode, 
      String errorMsg,}){
    _data = data;
    _errorCode = errorCode;
    _errorMsg = errorMsg;
}

  ApiRsponse.fromJson(dynamic json) {
    _data = json['data'];
    _errorCode = json['errorCode'];
    _errorMsg = json['errorMsg'];
  }
  String _data;
  int _errorCode;
  String _errorMsg;

  String get data => _data;
  int get errorCode => _errorCode;
  String get errorMsg => _errorMsg;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['data'] = _data;
    map['errorCode'] = _errorCode;
    map['errorMsg'] = _errorMsg;
    return map;
  }

}