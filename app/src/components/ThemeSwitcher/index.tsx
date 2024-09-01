'use client'
import { Switch } from '@nextui-org/react'
import { useTheme } from 'next-themes'
import { useEffect, useState } from 'react'
import { MoonIcon } from '../MoonIcon'
import { SunIcon } from '../SunIcon'

export const ThemeSwitcher = () => {
  const { theme, setTheme } = useTheme()
  const [mounted, setMounted] = useState(false)

  useEffect(() => setMounted(true), [])

  if (!mounted) return null

  return (
    <Switch
      defaultSelected
      size="lg"
      color="primary"
      isSelected={theme === 'dark'}
      onValueChange={() => setTheme(theme === 'dark' ? 'light' : 'dark')}
      thumbIcon={({ isSelected, className }) =>
        isSelected ? (
          <MoonIcon className={className} />
        ) : (
          <SunIcon className={className} />
        )
      }
    />
  )
}
