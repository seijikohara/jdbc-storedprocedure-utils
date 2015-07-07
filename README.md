# jdbc-storedprocedure-utils
Stored procedures utils for JDBC

## Usage

### Call procedure

```java
Procedure procedure = new Procedure("PROCEDURE_NAME");
InParamater inParam1 = new InParamater("VALUE", SQLType.CHAR, 5);
InParamater inParam2 = new InParamater(10, SQLType.INTEGER);
OutParamater outParam1 = new OutParamater("OUT_PARAM_NAME", SQLType.VARCHAR);
Procedure.Return callResult = procedure.execute(
  connection,
  inParam1,
  inParam2,
  outParam1);

// Get OUT paramter value
callResult.getString(outParam1);
```

### Call function

```java
Function function = new Function("FUNCTION_NAME");
InParamater inParam1 = new InParamater("VALUE", SQLType.CHAR, 5);
InParamater inParam2 = new InParamater(10, SQLType.INTEGER);
OutParamater outParam1 = new OutParamater("OUT_PARAM_NAME", SQLType.VARCHAR);
Function.Return callResult = function.execute(
  connection,
  SQLType.VARCHAR, // Function return type
  inParam1,
  inParam2,
  outParam1);

// Get function return value
callResult.getReturnString();

// Get OUT paramter value
callResult.getString(outParam1);
```
