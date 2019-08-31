import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(
    home: Home(),
    debugShowCheckedModeBanner: false,
  ));
}

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  String _infoText = "Informe seus dados";
  GlobalKey<FormState> _formkey = GlobalKey();
  TextEditingController weightController = TextEditingController();
  TextEditingController heightController = TextEditingController();

  void calculate() {
    setState(() {
      //_infoText= "sadojwsijfgisjgfisjd";
      double weight = double.parse(weightController.text);
      double height = double.parse(heightController.text) / 100;
      double imc = weight / (height * height);
      if (imc <= 18.6) {
        _infoText = "Abaixo do peso(${imc.toStringAsPrecision(4)})";
      } else if (imc > 18.6 && imc < 24.9) {
        _infoText = "Peso Normal(${imc.toStringAsPrecision(4)})";
      } else if (imc > 18.6 && imc < 24.9) {
        _infoText = "Sobrepeso(${imc.toStringAsPrecision(4)})";
      } else if (imc >= 24.9 && imc < 34.9) {
        _infoText = "Obesidade grau I(${imc.toStringAsPrecision(4)})";
      } else if (imc >= 34.9 && imc < 40) {
        _infoText = "Obesidade grau II(${imc.toStringAsPrecision(4)})";
      } else if (imc >= 40) {
        _infoText = "Obesidade grau III(${imc.toStringAsPrecision(4)})";
      }
    });
  }

  void resetFilds(){
    weightController.text="";
    heightController.text="";
    setState(() {
      _infoText = "Informe seus dados";
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Calculadora de IMC"),
        centerTitle: true,
        backgroundColor: Colors.green,
        actions: <Widget>[
          IconButton(
            icon: Icon(Icons.refresh),
            onPressed: () {
              resetFilds();
            },
          )
        ],
      ),
      backgroundColor: Colors.white,
      body: SingleChildScrollView(
        padding: EdgeInsets.all(10.0),
        child: Form(
          key: _formkey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              Icon(Icons.person_outline, size: 120.0, color: Colors.green),
              TextFormField(
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  labelText: "Peso (KG)",
                  labelStyle: TextStyle(color: Colors.green),
                ),
                style: TextStyle(color: Colors.green, fontSize: 25.0),
                controller: weightController,
                validator: (value) {
                  if (value.isEmpty) {
                    return "Preencha o Peso";
                  }
                },
              ),
              TextFormField(
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  labelText: "Altura (cm)",
                  labelStyle: TextStyle(color: Colors.green),
                ),
                style: TextStyle(color: Colors.green, fontSize: 25.0),
                controller: heightController,
                validator: (value) {
                  if (value.isEmpty) {
                    return "Preencha a Altura";
                  }
                },
              ),
              Padding(
                padding: EdgeInsets.only(top: 10, bottom: 10),
                child: Container(
                  height: 50.0,
                  child: RaisedButton(
                    onPressed: () {
                      if (_formkey.currentState.validate()) {
                        calculate();
                        FocusScope.of(context).requestFocus(new FocusNode());
                      }
                    },
                    color: Colors.green,
                    child: Text(
                      "Calcular",
                      style: TextStyle(color: Colors.white, fontSize: 25.0),
                    ),
                  ),
                ),
              ),
              Text(
                _infoText,
                style: TextStyle(color: Colors.green, fontSize: 25.0),
                textAlign: TextAlign.center,
              )
            ],
          ),
        ),
      ),
    );
  }
}
