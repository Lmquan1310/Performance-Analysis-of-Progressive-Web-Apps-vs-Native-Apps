const canvas = document.getElementById('canvas');
const ctx = canvas.getContext('2d');

// Function to set canvas dimensions
function setCanvasDimensions() {
  canvas.width = window.innerWidth * 0.8;
  canvas.height = window.innerHeight * 0.8;
}
setCanvasDimensions();

let width = canvas.width;
let height = canvas.height;

// Initial triangle points
const p1 = { x: width / 2, y: 0 };
const p2 = { x: 0, y: height };
const p3 = { x: width, y: height };

let maxDepth = 5; // Default max depth

// Draw a filled triangle given three points
function drawTriangle(p1, p2, p3) {
  ctx.beginPath();
  ctx.moveTo(p1.x, p1.y);
  ctx.lineTo(p2.x, p2.y);
  ctx.lineTo(p3.x, p3.y);
  ctx.closePath();
  ctx.fill();
}

// Recursive function to draw the Sierpinski triangle
function sierpinski(p1, p2, p3, depth) {
  if (depth === 0) {
    drawTriangle(p1, p2, p3);
    return;
  }

  // Find midpoints of each edge
  const mid1 = {
    x: (p1.x + p2.x) / 2,
    y: (p1.y + p2.y) / 2
  };
  const mid2 = {
    x: (p2.x + p3.x) / 2,
    y: (p2.y + p3.y) / 2
  };
  const mid3 = {
    x: (p3.x + p1.x) / 2,
    y: (p3.y + p1.y) / 2
  };

  // Recursively draw the three smaller triangles
  sierpinski(p1, mid1, mid3, depth - 1);
  sierpinski(mid1, p2, mid2, depth - 1);
  sierpinski(mid3, mid2, p3, depth - 1);
}

// Function to draw the triangle and measure drawing time
function draw() {
  const startTime = performance.now();

  ctx.clearRect(0, 0, width, height); // Clear the canvas
  ctx.fillStyle = 'black'; // Set triangle color

  // Draw the Sierpinski triangle with the current maxDepth
  sierpinski(p1, p2, p3, maxDepth);

  const endTime = performance.now();
  const drawingTime = (endTime - startTime).toFixed(2);

  // Update drawing time display
  const timeDisplay = document.getElementById('drawingTime');
  timeDisplay.textContent = `Drawing time: ${drawingTime}ms`;
}

// Add event listener to the draw button
document.getElementById('drawButton').addEventListener('click', () => {
  const input = document.getElementById('maxDepth');
  const depth = parseInt(input.value, 10);

  // Validate the input depth
  if (!isNaN(depth) && depth >= 0 && depth <= 20) {
    maxDepth = depth;
    draw();
  } else {
    alert('Please enter a valid depth between 0 and 20.');
  }
});

// Handle window resize
window.addEventListener('resize', () => {
  setCanvasDimensions();
  width = canvas.width;
  height = canvas.height;

  // Update triangle points based on new dimensions
  p1.x = width / 2;
  p1.y = 0;
  p2.x = 0;
  p2.y = height;
  p3.x = width;
  p3.y = height;

  draw();
});

// Initial drawing
draw();
