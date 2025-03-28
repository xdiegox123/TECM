# **Regresión Logística**

## ¿Qué es la Regresión Logística?

La regresión logística es un algoritmo de **aprendizaje supervisado**. Aunque el nombre sugiere regresión, en realidad es un modelo de clasificación. Su objetivo es encontrar la probabilidad de que una observación pertenezca a una de dos clases.

---

## Ruta de implementacion de un modelo de Regresión
![pipeline](https://miro.medium.com/v2/resize:fit:1400/format:webp/0*CKEc4j27kiRRJFJ-.jpg)

1. **Obtencion de datos:** para este ejmeplo se utilizaran datos proporcionados por la libreria sklearn, pero se recomienda la siguente fuente:
   - [**Papers with code**](https://paperswithcode.com/datasets): Esta pagina esta enfocada en Inteligencia artificial, debido a ello cuenta con una gran variedad de de datos listos para su uso en el ambito de la inteligencia artificial.
2. **Procesamiento de datos:** Entramos en proceso de normalizacion, regularizacion, segmentacion y saneado de datos, en caso de ser necesario.
3. **Entrenamiento del modelo:** Para este punto, se debe haber elegido un modelo acorde a nuestras necesidades dadas por experiencia e intuicion para despues implemenentarlo en el entorno requerido.
4. **Evaluacion del modelo:** En este punto se evalua el comportamiento del modelo, comprobanto el comportamiento esperdo.
5. **Puesta a produccion:** Una vez que el modelo cumpla con los requisitos, se puede desplegar en su entorno de produccion.
6. **Monitoreo y Mantenimiento:** Se revisita constante mente para evitar peridas o ser re entrenado de ser necesario.

_En este documento se enfocara en los puntos del 3 al 4._

---
### **Entrenamiento del modelo**




## **¿Cómo funciona la Regresión Logística?**

## **1. Función Sigmoide**

La regresión logística se basa en la **función sigmoide**, que mapea cualquier valor real a un rango entre 0 y 1, ideal para representar probabilidades.

$$\left[ \sigma(z) = \frac{1}{1 + e^{-z}} \right]$$

Donde:

- $\left( z = W^T X \right)$ → Producto escalar entre el vector de características $\left( X \right)$ y el vector de pesos $\left( W \right)$.
- $\left( \sigma(z) \right)$ → Probabilidad de pertenecer a la clase positiva.

---

## **2. Función de Verosimilitud**

La función de verosimilitud mide la probabilidad de observar los datos actuales dados unos parámetros $\left( W \right)$.

$$\left[ L(W) = \prod\_{i=1}^{n} \sigma(W^T X_i)^{y_i} (1 - \sigma(W^T X_i))^{1 - y_i} \right]$$

Donde:

- $\left( y_i \right)$ → Etiqueta de la muestra $\left( i \right)$ (0 o 1).
- $\left( \sigma(W^T X_i) \right)$ → Probabilidad de pertenecer a la clase positiva.

Para facilitar la derivación, aplicamos el **logaritmo natural**:

$$\left[ \ell(W) = \log L(W) = \sum\_{i=1}^{n} \left[ y_i \log \sigma(W^T X_i) + (1 - y_i) \log(1 - \sigma(W^T X_i)) \right] \right]$$

---

## **3. Función de Pérdida: Cross-Entropy**

La **cross-entropy** mide la distancia entre las predicciones del modelo y las etiquetas reales.

$$\left[ J(W) = -\frac{1}{n} \sum\_{i=1}^{n} \left[ y_i \log(\sigma(W^T X_i)) + (1 - y_i) \log(1 - \sigma(W^T X_i)) \right] \right]$$

El objetivo es minimizar esta función durante el entrenamiento.

---

## **4. Derivación paso a paso**

### **Derivada del Logaritmo de la Verosimilitud**

Para encontrar el valor óptimo de los pesos $\left( W \right)$, calculamos la **derivada parcial** de la log-verosimilitud respecto a $\left( W \right)$:

$$\left[ \ell(W) = \sum\_{i=1}^{n} \left[ y_i \log(\sigma(W^T X_i)) + (1 - y_i) \log(1 - \sigma(W^T X_i)) \right] \right]$$

1. **Derivada parcial del primer término:**
   $$\left[ \frac{\partial}{\partial W} \sum\_{i=1}^{n} y_i \log(\sigma(W^T X_i)) \right]$$

Usamos la regla de la cadena:
$$\left[ \frac{\partial}{\partial W} \log(\sigma) = \frac{1}{\sigma} \cdot \frac{\partial \sigma}{\partial W} \right]$$

Derivada de la función sigmoide:
$$\left[ \frac{\partial \sigma}{\partial W} = \sigma(1 - \sigma) X_i \right]$$

Por lo tanto:
$$\left[ \frac{\partial}{\partial W} y_i \log(\sigma) = y_i \frac{1}{\sigma} \cdot \sigma(1 - \sigma) X_i = y_i (1 - \sigma) X_i \right]$$

2. **Derivada parcial del segundo término:**
   $$\left[ \frac{\partial}{\partial W} \sum\_{i=1}^{n} (1 - y_i) \log(1 - \sigma(W^T X_i)) \right]$$

Usamos la regla de la cadena nuevamente:
$$\left[ \frac{\partial}{\partial W} \log(1 - \sigma) = \frac{-1}{1 - \sigma} \cdot \frac{\partial \sigma}{\partial W} \right]$$

$$\left[ = \frac{-1}{1 - \sigma} \cdot \sigma(1 - \sigma) X_i = -\sigma X_i \right]$$

Por lo tanto:
$$\left[ \frac{\partial}{\partial W} (1 - y_i) \log(1 - \sigma) = - (1 - y_i) \sigma X_i \right]$$

3. **Gradiente completo:**
   Juntando ambas partes:

$$\left[ \frac{\partial \ell(W)}{\partial W} = \sum\_{i=1}^{n} X_i \left( y_i - \sigma(W^T X_i) \right) \right]$$

---

## **5. Algoritmo de Gradiente Ascendente**

Usamos **gradiente ascendente** para maximizar la log-verosimilitud (o minimizar la función de pérdida):

$$\left[ W^{(t+1)} = W^{(t)} + \alpha \sum\_{i=1}^{n} X_i \left( y_i - \sigma(W^T X_i) \right) \right]$$

Donde:

- $\left( \alpha \right)$ → Tasa de aprendizaje.
- $\left( t \right)$ → Iteración actual.

---

## **5.5. Algoritmo de Gradiente Descendente**

Usamos **gradiente descendente** para minimizar la función de pérdida:

$$\left[ W^{(t+1)} = W^{(t)} + \alpha \sum_{i=1}^{n} X_i \left( y_i - \sigma(W^T X_i) \right) \right]$$

Donde:

- $\left( \alpha \right)$ → Tasa de aprendizaje.
- $\left ( t \right)$ → Iteración actual.

## **6. Implementación en Python**

Aquí está la implementación básica del algoritmo en Python:

```python
import numpy as np

def sigmoid(z):
    return 1 / (1 + np.exp(-z))

def gradient(X, y, W):
    m = X.shape[0]
    predictions = sigmoid(X @ W)
    return np.dot(X.T, (y - predictions)) / m

# Generar datos de prueba
np.random.seed(42)
n_samples = 100
n_features = 2
X = np.random.rand(n_samples, n_features)
y = (X[:, 0] + X[:, 1] > 1).astype(int)

# Inicializar pesos aleatorios
W = np.random.rand(n_features)
alpha = 0.1
epochs = 1000

for epoch in range(epochs):
    W += alpha * gradient(X, y, W)
    if epoch % 100 == 0:
        loss = -np.mean(y * np.log(sigmoid(X @ W)) + (1 - y) * np.log(1 - sigmoid(X @ W)))
        print(f"Iteración {epoch}: Pérdida {loss:.4f}")

print("Pesos finales:", W)
```
