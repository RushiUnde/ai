import numpy as np
import matplotlib.pyplot as plt

# Define the centroid coordinates
centroid_x = 0.5
centroid_y = 0.5


X = np.linspace(0, 1, 100)
Y = np.linspace(0, 1, 100)


x, y = np.meshgrid(X, Y)


def membership_function(g, x, y):
    mem_val = g * np.sqrt((x - centroid_x)*2 + (y - centroid_y)*2)
   
    return np.where(mem_val == 0, 1, np.where(mem_val >= 1, 0, 1 - mem_val))


y1 = membership_function(1, x, y)
y2 = membership_function(4, x, y)

# Create contour plots to visualize the fuzzy sets
plt.figure(figsize=(12, 5))

plt.subplot(1, 2, 1)
plt.contourf(x, y, y1, cmap='Reds')
plt.colorbar(label='Membership Value')
plt.xlabel('X')
plt.ylabel('Y')
plt.title('Fuzzy Set y1 with Centroid at (0.5, 0.5)')

plt.subplot(1, 2, 2)
plt.contourf(x, y, y2, cmap='Reds')
plt.colorbar(label='Membership Value')
plt.xlabel('X')
plt.ylabel('Y')
plt.title('Fuzzy Set y2 with Centroid at (0.5, 0.5)')

plt.tight_layout()
plt.show()