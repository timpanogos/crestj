defmodule Stats do

  def sum([]) do
    0
  end

  def sum([h|t]) do
    h + sum(t)
  end

  def sample(f, 0) do
    []
  end

  def sample(f, n) do
    [f.()|sample(f, n-1)]
  end

    def max_list([]) do
    :undefined
  end

  def max_list([x]) do
    x
  end

  def max_list([h|t]) do
    max(h, max_list(t))
  end


end
