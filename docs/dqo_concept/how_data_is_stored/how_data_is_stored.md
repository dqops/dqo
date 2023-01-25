# How data is stored

Dqo stores data quality check's alerts and readings in a .parquet files.
You can find them in a private directory in an userhome folder:
`userhome\.data\`.

Here is a userhome folder structure.
``` java
userhome
├───.data                                                                   
│   ├───alerts //(1)                                                        
│   │   └───c=dqo-ai-testing                                                
│   │       └───t=dqo_ai_test_data.test_data_regex_sensor_179306422851143075
│   │           └───m=2022-05-01                                            
│   └───readings //(2)                                                            
│       └───c=dqo-ai-testing                                                
│           └───t=dqo_ai_test_data.test_data_regex_sensor_179306422851143075
│               └───m=2022-05-01                                               
├───.index                                                                  
├───rules                                                                   
├───sensors                                                                 
└───sources //(3)                                                                 
    └───dqo-ai-testing      
```

1. Folder with alerts
2. Folder with readings
3. Sources


After running

```
cloud sync data 
```

The data should appear in BigQuery, partitioned by month