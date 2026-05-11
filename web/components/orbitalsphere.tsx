"use client";

import { useRef, useMemo } from "react";
import { Canvas, useFrame } from "@react-three/fiber";
import { Points, PointMaterial } from "@react-three/drei";
import * as THREE from "three";

function spherePoints(count: number, radius: number) {
  const positions = new Float32Array(count * 3);
  for (let i = 0; i < count; i++) {
    const theta = Math.random() * Math.PI * 2;
    const phi = Math.acos(2 * Math.random() - 1);
    positions[i * 3] = radius * Math.sin(phi) * Math.cos(theta);
    positions[i * 3 + 1] = radius * Math.sin(phi) * Math.sin(theta);
    positions[i * 3 + 2] = radius * Math.cos(phi);
  }
  return positions;
}

// Neutral palette — works on both light and dark shadcn themes
const COLORS = {
  ring: "#888888",
  particles: "#aaaaaa",
  nodeA: "#cccccc",
  nodeB: "#888888",
  core: "#dddddd",
  wire: "#666666",
  line: "#999999",
};

function OrbitRing({
  radius,
  tilt,
  speed,
}: {
  radius: number;
  tilt: number;
  speed: number;
}) {
  const ref = useRef<THREE.Mesh>(null!);
  useFrame((_, delta) => {
    ref.current.rotation.y += delta * speed;
  });
  return (
    <mesh ref={ref} rotation={[tilt, 0, 0]}>
      <torusGeometry args={[radius, 0.007, 8, 120]} />
      <meshBasicMaterial color={COLORS.ring} transparent opacity={0.2} />
    </mesh>
  );
}

function NodeDot({
  position,
  bright,
}: {
  position: [number, number, number];
  bright?: boolean;
}) {
  const ref = useRef<THREE.Mesh>(null!);
  const offset = useRef(Math.random() * Math.PI * 2);
  useFrame(({ clock }) => {
    ref.current.scale.setScalar(
      1 + 0.22 * Math.sin(clock.getElapsedTime() * 2 + offset.current),
    );
  });
  return (
    <mesh ref={ref} position={position}>
      <sphereGeometry args={[0.045, 10, 10]} />
      <meshBasicMaterial
        color={bright ? COLORS.nodeA : COLORS.nodeB}
        transparent
        opacity={0.9}
      />
    </mesh>
  );
}

function ParticleCloud() {
  const ref = useRef<THREE.Points>(null!);
  const positions = useMemo(() => spherePoints(1600, 2.8), []);
  useFrame((_, delta) => {
    ref.current.rotation.x -= delta * 0.035;
    ref.current.rotation.y -= delta * 0.055;
  });
  return (
    <Points ref={ref} positions={positions} stride={3} frustumCulled={false}>
      <PointMaterial
        size={0.016}
        color={COLORS.particles}
        transparent
        opacity={0.35}
        depthWrite={false}
        sizeAttenuation
      />
    </Points>
  );
}

function CoreSphere() {
  const ref = useRef<THREE.Group>(null!);
  useFrame((_, delta) => {
    ref.current.rotation.y += delta * 0.14;
    ref.current.rotation.x += delta * 0.04;
  });

  const nodes: { pos: [number, number, number]; bright?: boolean }[] = [
    { pos: [0, 1.3, 0], bright: true },
    { pos: [1.1, -0.7, 0.5], bright: false },
    { pos: [-1.2, 0.3, 0.6], bright: true },
    { pos: [0.4, 0.9, -1.1], bright: false },
    { pos: [-0.5, -1.2, -0.5], bright: true },
    { pos: [0.9, 0.4, 1.0], bright: false },
  ];

  return (
    <group ref={ref}>
      {/* Wireframe sphere */}
      <mesh>
        <sphereGeometry args={[1.25, 24, 24]} />
        <meshBasicMaterial
          color={COLORS.wire}
          wireframe
          transparent
          opacity={0.07}
        />
      </mesh>

      {/* Center core */}
      <mesh>
        <sphereGeometry args={[0.16, 16, 16]} />
        <meshBasicMaterial color={COLORS.core} transparent opacity={0.95} />
      </mesh>

      <OrbitRing radius={1.6} tilt={0} speed={0.28} />
      <OrbitRing radius={1.6} tilt={Math.PI / 3} speed={-0.18} />
      <OrbitRing radius={1.6} tilt={Math.PI / 1.5} speed={0.16} />

      {nodes.map((n, i) => (
        <NodeDot key={i} position={n.pos} bright={n.bright} />
      ))}

      {nodes.slice(0, 4).map((n, i) => {
        const next = nodes[(i + 2) % nodes.length];
        const pts = [
          new THREE.Vector3(...n.pos),
          new THREE.Vector3(0, 0, 0),
          new THREE.Vector3(...next.pos),
        ];
        const curve = new THREE.CatmullRomCurve3(pts);
        const geo = new THREE.BufferGeometry().setFromPoints(
          curve.getPoints(20),
        );
        return (
          <line key={`l-${i}`}>
            <bufferGeometry attach="geometry" {...geo} />
            <lineBasicMaterial color={COLORS.line} transparent opacity={0.15} />
          </line>
        );
      })}
    </group>
  );
}

function Scene() {
  const groupRef = useRef<THREE.Group>(null!);
  useFrame(({ clock }) => {
    const t = clock.getElapsedTime();
    groupRef.current.rotation.y = t * 0.07;
    groupRef.current.position.y = Math.sin(t * 0.38) * 0.07;
  });
  return (
    <group ref={groupRef}>
      <ParticleCloud />
      <CoreSphere />
    </group>
  );
}

export function OrbitalSphere() {
  return (
    <Canvas
      camera={{ position: [0, 0, 5], fov: 45 }}
      style={{ width: "100%", height: "100%" }}
      gl={{ antialias: true, alpha: true }}
    >
      <ambientLight intensity={0.6} />
      <Scene />
    </Canvas>
  );
}
