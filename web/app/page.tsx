import { Navbar } from "@/components/navbar";
import { HeroSection } from "@/components/hero-section";
import { FeaturesSection } from "@/components/feature-section";
import { HowItWorksSection } from "@/components/how-it-work";
import { ScaleSection } from "@/components/scale-section";
import { CtaSection, Footer } from "@/components/cta-section";

export default function HomePage() {
  return (
    <main className="bg-[#050A14] text-white antialiased overflow-x-hidden">
      <Navbar />
      <HeroSection />
      <FeaturesSection />
      <HowItWorksSection />
      <ScaleSection />
      <CtaSection />
      <Footer />
    </main>
  );
}
