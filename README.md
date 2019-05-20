# WebUntisService

Java SE Application that periodically checks the webuntis api for updates and manages the epapers via mqtt

## Response examples

### GET /updatedAt?room=E23

1558346425953

### GET /state?room=E23 

```json
{
  "room": {
    "id": 32,
    "name": "E23",
    "longName": "E23"
  },
  "lessonsPerDay": {
    "20190520": [
      {
        "id": 237913,
        "date": "20190520",
        "startTime": 800,
        "endTime": 850,
        "teachers": [
          {
            "id": 14,
            "shortName": "BREN"
          }
        ],
        "subject": {
          "id": 35,
          "name": "0RK"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 239248,
        "date": "20190520",
        "startTime": 1150,
        "endTime": 1240,
        "teachers": [
          {
            "id": 27,
            "shortName": "GIRI"
          }
        ],
        "subject": {
          "id": 37,
          "name": "0D"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 239249,
        "date": "20190520",
        "startTime": 1245,
        "endTime": 1335,
        "teachers": [
          {
            "id": 27,
            "shortName": "GIRI"
          }
        ],
        "subject": {
          "id": 37,
          "name": "0D"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 312828,
        "date": "20190520",
        "startTime": 1435,
        "endTime": 1525,
        "teachers": [
          {
            "id": 102,
            "shortName": "SIEG"
          }
        ],
        "subject": {
          "id": 350,
          "name": "0ETH"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 312829,
        "date": "20190520",
        "startTime": 1530,
        "endTime": 1620,
        "teachers": [
          {
            "id": 102,
            "shortName": "SIEG"
          }
        ],
        "subject": {
          "id": 350,
          "name": "0ETH"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 266992,
        "date": "20190520",
        "startTime": 1000,
        "endTime": 1050,
        "teachers": [
          {
            "id": 119,
            "shortName": "WEGIN"
          }
        ],
        "subject": {
          "id": 396,
          "name": "0RWBW"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 266993,
        "date": "20190520",
        "startTime": 1055,
        "endTime": 1145,
        "teachers": [
          {
            "id": 119,
            "shortName": "WEGIN"
          }
        ],
        "subject": {
          "id": 396,
          "name": "0RWBW"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      }
    ],
    "20190521": [
      {
        "id": 306747,
        "date": "20190521",
        "startTime": 1150,
        "endTime": 1240,
        "teachers": [
          {
            "id": 14,
            "shortName": "BREN"
          }
        ],
        "subject": {
          "id": 35,
          "name": "0RK"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 245497,
        "date": "20190521",
        "startTime": 1000,
        "endTime": 1050,
        "teachers": [
          {
            "id": 50,
            "shortName": "KERS"
          }
        ],
        "subject": {
          "id": 52,
          "name": "0AM"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 245498,
        "date": "20190521",
        "startTime": 1055,
        "endTime": 1145,
        "teachers": [
          {
            "id": 50,
            "shortName": "KERS"
          }
        ],
        "subject": {
          "id": 52,
          "name": "0AM"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 252328,
        "date": "20190521",
        "startTime": 855,
        "endTime": 945,
        "teachers": [
          {
            "id": 117,
            "shortName": "URSCH"
          }
        ],
        "subject": {
          "id": 250,
          "name": "2D"
        },
        "klass": {
          "id": 380,
          "name": "1BHIF"
        }
      },
      {
        "id": 309526,
        "date": "20190521",
        "startTime": 1245,
        "endTime": 1335,
        "teachers": [
          {
            "id": 123,
            "shortName": "WILL"
          }
        ],
        "subject": {
          "id": 465,
          "name": "0GGS"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      }
    ],
    "20190522": [
      {
        "id": 245499,
        "date": "20190522",
        "startTime": 855,
        "endTime": 945,
        "teachers": [
          {
            "id": 50,
            "shortName": "KERS"
          }
        ],
        "subject": {
          "id": 52,
          "name": "0AM"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 318556,
        "date": "20190522",
        "startTime": 1000,
        "endTime": 1050,
        "teachers": [
          {
            "id": 50,
            "shortName": "KERS"
          }
        ],
        "subject": {
          "id": 52,
          "name": "0AM"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 260118,
        "date": "20190522",
        "startTime": 1055,
        "endTime": 1145,
        "teachers": [
          {
            "id": 53,
            "shortName": "KODR"
          },
          {
            "id": 53,
            "shortName": "KODR"
          }
        ],
        "subject": {
          "id": 365,
          "name": "0E1"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 266144,
        "date": "20190522",
        "startTime": 1245,
        "endTime": 1335,
        "teachers": [
          {
            "id": 114,
            "shortName": "TRAUN"
          }
        ],
        "subject": {
          "id": 395,
          "name": "0BOBW"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 266145,
        "date": "20190522",
        "startTime": 1340,
        "endTime": 1430,
        "teachers": [
          {
            "id": 114,
            "shortName": "TRAUN"
          }
        ],
        "subject": {
          "id": 395,
          "name": "0BOBW"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 309527,
        "date": "20190522",
        "startTime": 1435,
        "endTime": 1525,
        "teachers": [
          {
            "id": 123,
            "shortName": "WILL"
          }
        ],
        "subject": {
          "id": 465,
          "name": "0GGS"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 299821,
        "date": "20190522",
        "startTime": 800,
        "endTime": 850,
        "teachers": [
          {
            "id": 173,
            "shortName": "ERNE"
          }
        ],
        "subject": {
          "id": 538,
          "name": "0NWC"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      }
    ],
    "20190523": [
      {
        "id": 311620,
        "date": "20190523",
        "startTime": 1435,
        "endTime": 1525,
        "teachers": [
          {
            "id": 160,
            "shortName": "MISCH"
          }
        ],
        "subject": {
          "id": 36,
          "name": "0RE"
        },
        "klass": {
          "id": 407,
          "name": "4AHIF"
        }
      },
      {
        "id": 242323,
        "date": "20190523",
        "startTime": 800,
        "endTime": 850,
        "teachers": [
          {
            "id": 172,
            "shortName": "BRUEC"
          }
        ],
        "subject": {
          "id": 39,
          "name": "2E1"
        },
        "klass": {
          "id": 410,
          "name": "4BHIF"
        }
      },
      {
        "id": 242324,
        "date": "20190523",
        "startTime": 855,
        "endTime": 945,
        "teachers": [
          {
            "id": 172,
            "shortName": "BRUEC"
          }
        ],
        "subject": {
          "id": 39,
          "name": "2E1"
        },
        "klass": {
          "id": 410,
          "name": "4BHIF"
        }
      },
      {
        "id": 252329,
        "date": "20190523",
        "startTime": 1055,
        "endTime": 1145,
        "teachers": [
          {
            "id": 117,
            "shortName": "URSCH"
          }
        ],
        "subject": {
          "id": 250,
          "name": "2D"
        },
        "klass": {
          "id": 380,
          "name": "1BHIF"
        }
      },
      {
        "id": 309057,
        "date": "20190523",
        "startTime": 1530,
        "endTime": 1620,
        "teachers": [
          {
            "id": 54,
            "shortName": "KOECK"
          }
        ],
        "subject": {
          "id": 426,
          "name": "0PROO"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 279222,
        "date": "20190523",
        "startTime": 1000,
        "endTime": 1050,
        "teachers": [
          {
            "id": 54,
            "shortName": "KOECK"
          }
        ],
        "subject": {
          "id": 467,
          "name": "1PROO"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 279223,
        "date": "20190523",
        "startTime": 1055,
        "endTime": 1145,
        "teachers": [
          {
            "id": 54,
            "shortName": "KOECK"
          }
        ],
        "subject": {
          "id": 467,
          "name": "1PROO"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      }
    ],
    "20190524": [
      {
        "id": 311687,
        "date": "20190524",
        "startTime": 1245,
        "endTime": 1335,
        "teachers": [
          {
            "id": 160,
            "shortName": "MISCH"
          }
        ],
        "subject": {
          "id": 36,
          "name": "0RE"
        },
        "klass": {
          "id": 417,
          "name": "5BHIF"
        }
      },
      {
        "id": 299822,
        "date": "20190524",
        "startTime": 1000,
        "endTime": 1050,
        "teachers": [
          {
            "id": 173,
            "shortName": "ERNE"
          }
        ],
        "subject": {
          "id": 538,
          "name": "0NWC"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      },
      {
        "id": 299823,
        "date": "20190524",
        "startTime": 1055,
        "endTime": 1145,
        "teachers": [
          {
            "id": 173,
            "shortName": "ERNE"
          }
        ],
        "subject": {
          "id": 538,
          "name": "0NWC"
        },
        "klass": {
          "id": 393,
          "name": "2DHIF"
        }
      }
    ]
  }
}
```
