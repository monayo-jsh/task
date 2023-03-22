String.prototype.format = function() {
  let formatted = this;
  for( let arg in arguments ) {
    formatted = formatted.replace("{" + arg + "}", arguments[arg]);
  }
  return formatted;
}

Date.prototype.yyyyMMddHHmmss = function () {
  let yyyy = this.getFullYear().toString();
  let MM = pad(this.getMonth() + 1,2);
  let dd = pad(this.getDate(), 2);
  let HH = pad(this.getHours(), 2);
  let mm = pad(this.getMinutes(), 2)
  let ss = pad(this.getSeconds(), 2)

  return yyyy + "-" + MM + "-" + dd + " " + HH + ":" +mm + ":" + ss;
};

function pad(number, length) {
  let str = '' + number;
  while (str.length < length) {
    str = '0' + str;
  }
  return str;
}

function convJSONObject(dataArray) {
  let jsonObject = {};

  for (let data of dataArray) {
    jsonObject[data.name] = data.value;
  }

  return jsonObject;
}

function convFormData(dataArray) {
  let formData = new FormData();

  for (let data of dataArray) {
    formData.append(data.name, data.value);
  }

  return formData;
}