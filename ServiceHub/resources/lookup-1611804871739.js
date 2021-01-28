(function(window, undefined) {
  var dictionary = {
    "996d0549-9cdc-4543-a95b-bb831efb1e37": "Client Signup",
    "9d954f5b-159d-4fcc-a700-cc3eb4b85ff5": "Service Provider",
    "9cc6050b-ec38-4cd9-a878-3e321e6449bf": "Client Dashboard",
    "7faf7097-ce4f-44d5-93d7-4ab5b779e8ef": "LoginPage",
    "d12245cc-1680-458d-89dd-4f0d7fb22724": "Splash Screen",
    "9ebe2c67-939b-42aa-87a6-791fe0cac427": "Avability",
    "13bf2996-533f-47db-ae9c-9f79f6056fe4": "Account Selection Page",
    "f8dd88b6-939e-49a4-8bcd-3dde8e5e3082": "Service Provider Dashboard",
    "f39803f7-df02-4169-93eb-7547fb8c961a": "Template 1",
    "bb8abf58-f55e-472d-af05-a7d1bb0cc014": "default"
  };

  var uriRE = /^(\/#)?(screens|templates|masters|scenarios)\/(.*)(\.html)?/;
  window.lookUpURL = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, url;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      url = folder + "/" + canvas;
    }
    return url;
  };

  window.lookUpName = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, canvasName;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      canvasName = dictionary[canvas];
    }
    return canvasName;
  };
})(window);