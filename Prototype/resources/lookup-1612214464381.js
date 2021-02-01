(function(window, undefined) {
  var dictionary = {
    "e972d516-c9a1-43c1-aa88-323961702444": "SP Availibility",
    "996d0549-9cdc-4543-a95b-bb831efb1e37": "Client Signup",
    "9d954f5b-159d-4fcc-a700-cc3eb4b85ff5": "Service Provider",
    "3b2c8cc3-3fe5-4117-9ab6-4b0ae5206476": "Single Plumber",
    "7faf7097-ce4f-44d5-93d7-4ab5b779e8ef": "LoginPage",
    "9ebe2c67-939b-42aa-87a6-791fe0cac427": "Avability",
    "f117f9f7-5581-4aea-a52b-018cb8a6a13b": "Servicedash",
    "f904b66a-8209-437d-8524-6a62b59ef728": "Clientdash",
    "9cc6050b-ec38-4cd9-a878-3e321e6449bf": "Client Dashboard",
    "c0f28b13-7996-442f-b150-a809da0c299a": "Service Provider Dashboard",
    "f1374d85-ec35-4a80-a3b5-1b95b6351f13": "Profile",
    "48dca195-23b8-4c29-80d0-80a4799e80f6": "All Plumbers",
    "d12245cc-1680-458d-89dd-4f0d7fb22724": "Splash Screen",
    "13bf2996-533f-47db-ae9c-9f79f6056fe4": "Account Selection Page",
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