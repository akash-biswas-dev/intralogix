"use client";

import { createContext, ReactNode, useContext, useState } from "react";

const LoadingContext = createContext<{
  isLoading: boolean;
  setLoading: (isLoad: boolean) => void;
}>({
  isLoading: false,
  setLoading: () => {},
});

export function LoadingContextProvider({ children }: { children: ReactNode }) {
  const [isLoading, setIsLoading] = useState(false);

  const updateLoading = (isLoad: boolean) => {
    setIsLoading(isLoad);
  };

  return (
    <LoadingContext value={{ isLoading, setLoading: updateLoading }}>
      {children}
      {isLoading && <LoadingPage />}
    </LoadingContext>
  );
}

export default function useLoadingContext() {
  return useContext(LoadingContext);
}

export const LoadingPage = () => {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-background/25 backdrop-blur-[2px]">
      <div className="flex flex-col items-center gap-4 bg-card p-8 rounded-lg border shadow-lg">
        {/* Spinner */}
        <div className="relative h-16 w-16">
          <div className="absolute inset-0 rounded-full border-4 border-muted"></div>
          <div className="absolute inset-0 rounded-full border-4 border-primary border-t-transparent animate-spin"></div>
        </div>

        {/* Loading text */}
        <div className="flex flex-col items-center gap-2">
          <p className="text-sm font-medium text-foreground">Loading</p>
          <div className="flex gap-1">
            <span className="h-1.5 w-1.5 rounded-full bg-primary animate-bounce [animation-delay:-0.3s]"></span>
            <span className="h-1.5 w-1.5 rounded-full bg-primary animate-bounce [animation-delay:-0.15s]"></span>
            <span className="h-1.5 w-1.5 rounded-full bg-primary animate-bounce"></span>
          </div>
        </div>
      </div>
    </div>
  );
};
