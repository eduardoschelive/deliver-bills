'use client'
import { QueryProvider, UIProvider } from '@/providers'
import { ThemeProvider } from 'next-themes'
import { PropsWithChildren } from 'react'

export function ProvidersWrapper({ children }: PropsWithChildren) {
  return (
    <QueryProvider>
      <UIProvider>
        <ThemeProvider>{children}</ThemeProvider>
      </UIProvider>
    </QueryProvider>
  )
}
